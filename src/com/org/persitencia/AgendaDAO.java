package com.org.persitencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import com.org.model.Eventos;
import com.org.model.agenda.Agenda;
import com.org.model.agenda.AgendaMembro;
import com.org.model.membro.Membro;

import static java.util.Collections.singletonList;

public class AgendaDAO {

	static MongoClient mongo = null;
	static FindIterable<Document> cursor = null;

	public static void add(Agenda agenda) {

		try {

			mongo = new MongoClient("localhost", 27017);
			MongoDatabase db = mongo.getDatabase("LetsGo");

			MongoCollection<Document> col = db.getCollection("agenda");

			Gson g = new GsonBuilder().create();
			String strAgenda = g.toJson(agenda);

			System.out.println(strAgenda);

			Map<String, Object> map = new Gson().fromJson(strAgenda, new TypeToken<HashMap<String, Object>>() {
			}.getType());

			Document doc = new Document(map);

			col.insertOne(doc);
		} finally {
			mongo.close();
		}
	}

	public static List<Agenda> get() {
		Agenda agenda = new Agenda();
		return get(agenda);
	}

	public static List<Agenda> get(Agenda agenda) {

		mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("LetsGo");
		MongoCollection<Document> collection = db.getCollection("agenda");

	
		
		if (agenda.getId() != null && !agenda.getId().trim().isEmpty()) {
			cursor = collection.find(Filters.eq("_id", new ObjectId(agenda.getId())));
		} else if (agenda.getNome() != null && !agenda.getNome().trim().isEmpty()) {
			cursor = collection.find(Filters.eq("nome", agenda.getNome()));
		} else {
			cursor = collection.find(new Document());
		}

		List<Agenda> la = new ArrayList<>();

		if (cursor != null) {
			Gson gson = new Gson();

			for (Document doc : cursor) {
				agenda =gson.fromJson(doc.toJson(), Agenda.class);
				
				agenda.setId(doc.get("_id").toString());

				// map = a.getMapMembros();

				if (agenda.getAgendaMembro().size() > 0) {
					agenda.getAgendaMembro().forEach(m -> {
						List<Membro> membros =  MembroDAO.get(new Membro(m.getMembro()));
						
						for(Membro mm: membros)
							m.setoMembro(mm);
						});
				}

				// all events
				List<Eventos> e1 = new ArrayList<>();
				Eventos e = new Eventos("", agenda.getId());
				e1.addAll(EventoDAO.get(e));
				//AgendaEventos ae = new AgendaEventos(e1);
				//if(e1.size()>0) {
				//	a.setListaEventos(e1);
				//}
				
				agenda.setEventos(e1);
				la.add(agenda);
			}
		}

		//cursor.iterator().close();
		mongo.close();

		
		
		return la;

	}

	public static void update(Agenda agenda) {

		mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("LetsGo");
		MongoCollection<Document> collection = db.getCollection("agenda");

		Document doc = new Document("ativo", agenda.isAtivo()).append("dataCriacao", agenda.getDataCriacao())
				.append("nome", agenda.getNome()).append("imagePath", agenda.getImagePath())
				.append("sobre", agenda.getSobre()).append("Eventos", agenda.getEventos());

		Bson updateValue = new Document("$set", doc);

		UpdateResult updateResult = collection.updateOne(Filters.eq("_id", new ObjectId(agenda.getId())), updateValue);

		System.out.println("Number of record updated:- " + updateResult.getModifiedCount());

		mongo.close();

	}

	public static void newEvento(Eventos evento) {
		mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("LetsGo");
		MongoCollection<Document> collection = db.getCollection("ev");

		// adcionar evento a agenda

		//eventos.forEach(e -> {

			Document doc = new Document("eventos", evento.getId());
			Bson filter = new Document("_id", new ObjectId(evento.getAgenda()));
			collection.updateOne(filter, Updates.push("listaEventos", doc));
		//});

		// altera documento membro
		/*
		 * MongoCollection<Document> collectionM = db.getCollection("membro"); Document
		 * docm = new Document("agendas", membro.getAgenda()); Bson filterm = new
		 * Document("_id", new ObjectId(membro.getMembro()));
		 * collectionM.updateOne(filterm, Updates.push("agendas", docm));
		 */

		mongo.close();

	}

	/*public static void addMembroToAgenda(AgendaMembro membro) {
		mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("LetsGo");
		MongoCollection<Document> collection = db.getCollection("agenda");

		// altera o documento agenda
		Document doc = new Document("membro", membro.getMembro()).append("tipo", membro.getTipo());
		Bson filter = new Document("_id", new ObjectId(membro.getAgenda()));
		collection.updateOne(filter, Updates.push("membros", doc));

		// altera documento membro
		MongoCollection<Document> collectionM = db.getCollection("membro");
		Document docm = new Document("agendas", membro.getAgenda());
		Bson filterm = new Document("_id", new ObjectId(membro.getMembro()));
		collectionM.updateOne(filterm, Updates.push("agendas", docm));

		mongo.close();

	}*/

	public static void addEventosOnAgenda(Eventos evento) {

		mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("LetsGo");
		MongoCollection<Document> collection = db.getCollection("agenda");

		// altera o documento agenda
		Document doc = new Document("evento", evento.getId());
		Bson filter = new Document("_id", new ObjectId(evento.getAgenda()));
		collection.updateOne(filter, Updates.push("listaEventos", doc));

		mongo.close();

	}
	
	public static List<Agenda> getAgendaPorMembro(Membro membro) {
		
		mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("LetsGo");
		MongoCollection<Document> collection = db.getCollection("agenda");
		
		
		FindIterable<Document> findIterable = collection.find(Document.parse("{'agendaMembro.membro' : '5abe7f060158a71af07edb02'}")); //, " + 
		//Filters.or(Document.parse("{tipo: 'ADMINISTRADOR'}"), Document.parse("{tipo: 'SEGUIDOR'}")))));
	
		///while(findIterable.iterator().hasNext()) {
		
		List<Agenda> la = new ArrayList<>();
		
		Gson gson = new Gson();
		
		for(Document d : findIterable) {
			System.out.println(d.toString());
			Agenda a = gson.fromJson(d.toJson(), Agenda.class);
			la.add(a);
		}
			
		
		return la;
		//}
	}
	
	public static void getAgendaPorMembroSpark(Membro membro){
		
		
		SparkSession spark = SparkSession.builder()
			      .master("local")
			      .appName("MongoSparkConnectorIntro")
			      .config("spark.mongodb.input.uri", "mongodb://localhost/LetsGo.agenda")
			      .config("spark.mongodb.output.uri", "mongodb://localhost/LetsGo.agenda")
			      .getOrCreate();

			    // Create a JavaSparkContext using the SparkSession's SparkContext object
			    JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

			    /*Start Example: Read data from MongoDB************************/
			    JavaMongoRDD<Document> rdd = MongoSpark.load(jsc);
			    /*End Example**************************************************/
			    
			    
			    
			    /*Start Example: Use aggregation to filter a RDD***************/
			    JavaMongoRDD<Document> aggregatedRdd = rdd.withPipeline(
			      singletonList(
			        Document.parse(" \"agendaMembro\": { id : " + membro.getId()  +" } } "))
			      );
			    /*End Example**************************************************/

			    // Analyze data from MongoDB
			    System.out.println(rdd.count());
			    System.out.println(rdd.first().toJson());
			   
			    System.out.println(membro.getId());
			   System.out.println(aggregatedRdd.collect());			    
			    
			    //rdd.

			    jsc.close();
		
		
		
	}
	

}
