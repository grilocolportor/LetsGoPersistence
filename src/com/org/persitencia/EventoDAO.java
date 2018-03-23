package com.org.persitencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.org.model.Eventos;
import com.org.model.agenda.Agenda;
import com.org.model.membro.Membro;

public class EventoDAO {

	static MongoClient mongo = null;
	static FindIterable<Document> cursor = null;
	
	public static void addEventos(Eventos evento) {
		
		mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("LetsGo");
		MongoCollection<Document> collection = db.getCollection("eventos");
		
		Gson g = new Gson();
		String strEvento = g.toJson(evento);

		System.out.println(strEvento);

		Map<String, Object> map = new Gson().fromJson(strEvento, new TypeToken<HashMap<String, Object>>() {
		}.getType());

		Document doc = new Document(map);

		collection.insertOne(doc);
		
		//Agenda agenda = new Agenda(evento.getAgenda());
		//agenda = AgendaDAO.get(agenda).get(0);
		
		//List<Eventos> le = new ArrayList<>();
		//le.add(evento);
		//agenda.setListaEventos(le);
		
		mongo.close();
		
	}
	
	public static List<Eventos> get() {
		Eventos evento = new Eventos();
		return get(evento);
	}

	public static List<Eventos> get(Eventos evento) {

		mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("LetsGo");
		MongoCollection<Document> collection = db.getCollection("eventos");

		
		Document d = new Document();
		if (evento.getId() != null && !evento.getId().trim().isEmpty()) {
			d.append("_id", new ObjectId(evento.getId()));
			
			//cursor = collection.find(Filters.eq("_id", new ObjectId(evento.getId())));
		} else if (evento.getAgenda() != null && !evento.getAgenda().trim().isEmpty()) {
			d.append("agenda", evento.getAgenda());
			//cursor = collection.find(Filters.eq("agenda", evento.getAgenda()));
		} else if (evento.getData()!=null && !evento.getData().trim().isEmpty()) {
			d.append("data", evento.getData());
			//cursor = collection.find(Filters.and(filters));
		} else if (evento.getHora()!=null && !evento.getHora().trim().isEmpty()) {
			d.append("hora", evento.getHora());
		}else if (evento.getNome()!=null && !evento.getNome().trim().isEmpty()) {
			d.append("nome", evento.getNome());
		}
		else if (evento.getSobre()!=null && !evento.getSobre().trim().isEmpty()) {
			d.append("sobre", evento.getSobre());
		}
		/*else {
			cursor = collection.find();
		}*/
		
		if(!d.isEmpty()) {
			cursor = collection.find(Filters.and(d));
		}else {
			cursor = collection.find(new Document());
		}
		
		
		List<Eventos> la = new ArrayList<>();

		//cursor.map(mapper)
		
		if (cursor != null) {
			Gson gson = new Gson();
			
			for (Document doc : cursor) {
				Eventos e = new Eventos();
				e = gson.fromJson(doc.toJson(), Eventos.class);
				e.setId(doc.get("_id").toString());

				la.add(e);
			}
		}

		cursor.iterator().close();
		mongo.close();

		return la;

	}
	
	public void updateEventos(Eventos evento) {
		
		mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("LetsGo");
		MongoCollection<Document> collection = db.getCollection("evento");

		// adcionar evento a agenda

		//eventos.forEach(e -> {

			Document doc = new Document("eventos", evento.getId());
			Bson filter = new Document("_id", new ObjectId(evento.getAgenda()));
			collection.updateOne(filter, Updates.push("listaEventos", doc));
		//});
			
			mongo.close();
		
	}
	
	

}
