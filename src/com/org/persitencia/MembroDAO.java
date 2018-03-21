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
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.org.model.membro.MembroContatos;
import com.org.model.membro.Membro;

public class MembroDAO {

	static MongoClient mongo = null;
	static FindIterable<Document> cursor = null;

	public static void add(Membro membro) {

		try {

			mongo = new MongoClient("localhost", 27017);
			MongoDatabase db = mongo.getDatabase("LetsGo");

			MongoCollection<Document> col = db.getCollection("membro");

			Gson g = new Gson();
			String strMembro = g.toJson(membro);

			Map<String, Object> map = new Gson().fromJson(strMembro, new TypeToken<HashMap<String, Object>>() {
			}.getType());

			Document doc = new Document(map);

			col.insertOne(doc);
		} finally {
			mongo.close();
		}
	}

	public static List<Membro> get() {
		Membro membros = new Membro();
		return get(membros);
	}

	public static List<Membro> get(Membro membro) {

		mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("LetsGo");
		MongoCollection<Document> collection = db.getCollection("membro");

		if (membro.getId() != null && !membro.getId().trim().isEmpty()) {
			cursor = collection.find(Filters.and(Filters.eq("_id", new ObjectId(membro.getId())), Filters.eq("ativo", true)));
		} else if (membro.getNumeroCelular() != null && !membro.getNumeroCelular().trim().isEmpty()) {
			cursor = collection.find(Filters.eq("numeroCelular", membro.getNumeroCelular()));
		} else {
			cursor = collection.find(new Document());
		}

		List<Membro> lm = new ArrayList<>();

		if (cursor != null) {
			Gson gson = new Gson();
			System.out.println(cursor.toString());
			for (Document doc : cursor) {
				Membro m = new Membro();
				m = gson.fromJson(doc.toJson(), Membro.class);
				m.setId(doc.get("_id").toString());
				lm.add(m);
			}
		}

		cursor.iterator().close();
		mongo.close();

		return lm;

	}

	public static void update(Membro membro) {

		mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("LetsGo");
		MongoCollection<Document> collection = db.getCollection("membro");

		Document doc = new Document("ativo", membro.isAtivo()).append("dataEntrada", membro.getDataEntrada())
				.append("email", membro.getEmail()).append("imagePath", membro.getImagePath())
				.append("nome", membro.getNome()).append("numeroCelular", membro.getNumeroCelular());

		Bson updateValue = new Document("$set", doc);

		UpdateResult updateResult = collection.updateOne(Filters.eq("_id", new ObjectId(membro.getId())), updateValue);

		System.out.println("Number of record updated:- " + updateResult.getModifiedCount());

		mongo.close();

	}

	public static void addContato(MembroContatos membroContato) {
		
		mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("LetsGo");
		MongoCollection<Document> collection = db.getCollection("membro");
		
		final Membro membro = new Membro();
		membro.setNumeroCelular(membroContato.getMembro());
		
		membroContato.getContato().forEach(c ->{
			//verifica se o contato ja é usuario
			Membro contato = new Membro();
			contato.setNumeroCelular(c);
			contato = MembroDAO.get(contato).get(0);
			
			if(contato.getId()!=null && !contato.getId().trim().isEmpty()) {
				
				Bson docMembro = new Document("", c);;//novo contato
				Bson filterMembro = new Document("numeroCelular", membro.getNumeroCelular());//filtra o membro para add o contato
				collection.updateOne(filterMembro, Updates.push("contatos", docMembro),  (new UpdateOptions()).upsert(true));// add o contato no membro filtardo
				
				Bson docContato = new Document("", membro.getNumeroCelular());//novo contato
				Bson filterContato = new Document("numeroCelular", c);//filtra o membro para add o contato
				collection.updateOne(filterContato, Updates.push("contatos", docContato),  (new UpdateOptions()).upsert(true));
			}
		});
		
		
		mongo.close();

		
	}

}
