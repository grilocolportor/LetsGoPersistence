package com.org.resources;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.org.model.Eventos;
import com.org.model.Localizacao;
import com.org.model.agenda.Agenda;
import com.org.model.agenda.AgendaMembro;
import com.org.model.agenda.Agendas;
import com.org.model.membro.MembroContatos;
import com.org.model.membro.Membros;
import com.org.model.membro.Membro;
import com.org.persitencia.AgendaDAO;
import com.org.persitencia.EventoDAO;
import com.org.persitencia.MembroDAO;



@Path("/ws")
public class LetsGo {
	
	@Path("/start")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response start() {
		
		Membro membro = new Membro("5aa9813cc7d7412b985c9851");
		AgendaDAO.getAgendaPorMembro(membro);
		
		return
				
		//		getAgenda();
				
		//return pGetEvento();
		
		//return pAddEvento();
		
		//  pAddAgenda();
		
		//pAddmembro();
		//getMembro();
				
				
		
		 Response.status(200).entity("Funciounou").header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();

	}
	
	
	//-----------------------------------Membros-------------------------------------
	
	@Path("/addmembro")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMembro(Membro membro) {
		
		LocalDateTime localDateTime = LocalDateTime.now();
		String localDate = localDateTime.toString();
		System.out.println(localDate);

		Gson gson = new Gson();

		membro.setDataEntrada(localDate);
		

		List<Membro> lu = MembroDAO.get(membro);
		if (!lu.isEmpty() && lu.get(0).getNumeroCelular().contentEquals(membro.getNumeroCelular())) {
			return Response.status(200).entity("Número de telefone ja existe")
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		} else {

			MembroDAO.add(membro);
			
			Membros membros = new Membros();
			
			membros.setMembros(MembroDAO.get(membro));
			
			
			String retorno = "{ \"membro\" :"  +  gson.toJson(membros.getMembros().get(0)) + "}";

			System.out.println(retorno);

			return Response.status(200).entity(retorno).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}
	}
	
	@Path("getmembrotodos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response getMembro(){
		Membro membro = new Membro();
		return getMembro(membro);
	}
	
/*	@Path("getagendaspormembro")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response getAgendaPorMembro(Membro membro){
		
		
		Membro membro = new Membro();
		
		
		return Response.status(200).entity(retorno).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		
	}*/
	
	@Path("getmembrofiltro")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static Response getMembro(Membro membro){
		
	
		
		List<Membro> listMembro = new ArrayList<>();
		
		listMembro = MembroDAO.get(membro);
		
		
		
		Gson gson = new Gson();
		
		String retorno = "";
		
		if(!listMembro.isEmpty()) {
			if(listMembro.size()>1) {
				Membros membros = new Membros();
				membros.setMembros(MembroDAO.get(membro));
				retorno = gson.toJson(membros);
			}else {
				membro = listMembro.get(0);
			}
			
		}else {
			retorno = gson.toJson(new Membro());
		}
		//System.out.println(retorno);

		return Response.status(200).entity(retorno).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}
	
	@Path("updatemembro")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  Response updateMembro(Membro membro){

		membro = MembroDAO.get().get(2);
		
		membro.setAtivo(false);
		
		MembroDAO.update(membro);

		List<Membro>ma = new ArrayList<>();
		ma = MembroDAO.get(membro);
		
		Gson gson = new Gson();
		String retorno = ""; 
		for(Membro r : ma) {
			retorno = retorno + " <--> " + gson.toJson(r);
		}

		//System.out.println(retorno);

		return Response.status(200).entity(retorno).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}
	
	
	@Path("/addcontato")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addContato(MembroContatos membroContato) {
		
		List<String> contatos = new ArrayList<>();
		contatos.add("+5581999999996");
		contatos.add("+5581999999995");
		contatos.add("+5581999999990");
		
		membroContato = new MembroContatos();
		membroContato.setMembro("+5581999999999");
		membroContato.setContato(contatos);
		
		
		MembroDAO.addContato(membroContato);
		
		Membro m = new Membro();
		m.setNumeroCelular(membroContato.getMembro());
		
		List<Membro> lm = MembroDAO.get(m);
		
		Gson gson = new Gson();
		String retorno = ""; 
		for(Membro r : lm) {
			retorno = retorno + " <--> " + gson.toJson(r);
		}

		return Response.status(200).entity(retorno).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}
	
	//---------------------------------Eventos-----------------------------------------------
	
	
	@Path("/addeventos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addEvento(Eventos evento) {
		
		evento = new Eventos();
		evento.setAgenda("5a6640c6af19c7024c163e85");
		evento.setData("01/02/2018");
		evento.setHora("09:00");
		evento.setImagePath("evento/imagepath/evento1.png");
		Localizacao loc = new Localizacao("928918292", "9934938439");
		evento.setLocalizacao(loc);
		
		EventoDAO.addEventos(evento);
		
		
		
		/*Gson gson = new Gson();
		String retorno = ""; 
		for(Membros r : lm) {
			retorno = retorno + " <--> " + gson.toJson(r);
		}*/
		
		return Response.status(200).entity("OK").header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}
	
	//--------------------------------- Agendas-----------------------------------------------
	
	@Path("/addagenda")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAgenda(Agenda agenda) {
		
		LocalDateTime localDateTime = LocalDateTime.now();
		String localDate = localDateTime.toString();
		System.out.println(localDate);

		Gson gson = new Gson();

		
		
		//String json = gson.toJson(lmap);
		
		//String agendaTemp = "{ativo: 'true', dataCriacao:" + localDate.toString() + ",  imagePath:'c:/teste/teste/teste6.png', nome:'TESTE1', sobre: '', membros : " + json + " }";
		
		//System.out.println(agendaTemp);
		
		//agenda = gson.fromJson(agendaTemp, Agenda.class);
		
		
		List<Agenda> lu = AgendaDAO.get(agenda);
		if (!lu.isEmpty() && lu.get(0).getId().contentEquals(agenda.getId())) {
			return Response.status(200).entity("Essa Agenda ja foi criada")
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		} else {

			AgendaDAO.add(agenda);
			
			List<Agenda> m = new ArrayList<>();
			
			m = AgendaDAO.get(agenda);
			
			String retorno = gson.toJson(m.get(0));

			//System.out.println(retorno);

			return Response.status(200).entity(retorno).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		}
	}
	
	@Path("/getagendatodos")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  Response getAgenda(){
		Agenda agenda = new Agenda();
		return getAgenda(agenda);
	}
	
	@Path("/getagendafiltro")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  Response getAgenda(Agenda agenda){
		
		List<Agenda> a = new ArrayList<>();
		
		a = AgendaDAO.get(agenda);
		
		Agendas agendas = new Agendas( a);
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		Gson gson = new Gson();
		
		Type type = new TypeToken<List<Agendas>>() {}.getType();
		String retorno =   gson.toJson( agendas) ;
		
		//for(Agenda r : a) {
		//	retorno =   gson.toJson(r) + " " + retorno;
		//}
		
		System.out.println(retorno);
		
		
		//String teste = "{\"result\":[{\"nome\":\"TESTE\",\"sobre\":\"TESTE DE INCLUSAO DE AGENDAS\",\"imagePath\":\"c:/teste/image/imagePath\",\"dataCriacao\":\"2018-03-05\",\"id\":\"5a9d863114b8bd0b98f3271e\",\"agendaMembro\":[{\"membro\":\"5a614b57af19c75698205c2e\",\"agenda\":\"\",\"tipo\":\"ADMINISTRADOR\"}],\"ativo\":\"true\"}]}";
		
		//System.out.println(teste);
		
		return Response.status(200).entity(retorno).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}
	
	@Path("updateagenda")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  Response updateAgenda(Agenda agenda){

		/*agenda = AgendaDAO.get().get(2);
		
		agenda.setAtivo(true);
		LocalDateTime localDateTime = LocalDateTime.now();
		String localDate = localDateTime.toString();
		System.out.println(localDate);

	
		agenda.setSobre("Agenda atualizada2");*/
		
		AgendaDAO.update(agenda);

		List<Agenda> a = new ArrayList<>();
		a = AgendaDAO.get(agenda);
		
		Gson gson = new Gson();
		String retorno = ""; 
		for(Agenda r : a) {
			retorno = retorno + " <--> " + gson.toJson(r);
		}

		//System.out.println(retorno);

		return Response.status(200).entity(retorno).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}
	
	
	@Path("addmembroagenda")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  Response addMembroAgenda(AgendaMembro am) {
		
		Agenda agenda = new Agenda("5a6640c6af19c7024c163e85");
		
		
		List<AgendaMembro> lam = new ArrayList<>();
		//lam.addAll(agenda.getMembros());
		AgendaMembro amm = new AgendaMembro("5a614b57af19c75698205c2e", "ADMINISTRADOR", agenda.getId());
		lam.add(amm);
		
		//agenda.setMembros(lam);
		
		AgendaDAO.addMembroToAgenda(amm);
		
		List<Agenda> a = new ArrayList<>();
		a = AgendaDAO.get(agenda);
		
		Gson gson = new Gson();
		String retorno = gson.toJson(a.get(0));
		
		return Response.status(200).entity(retorno).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		
	}
	
	//--------------------EVENTOS----------------
	
	@Path("newEvento")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newEvento(Eventos evento) {
		
		EventoDAO.addEventos(evento);
		
		List<Eventos> lEventos = new ArrayList<>();
		lEventos = EventoDAO.get(evento);
		
		AgendaDAO.addEventosOnAgenda(lEventos.get(0));
		
		List<Agenda> lAgenda = new ArrayList<>();
		lAgenda = AgendaDAO.get(new Agenda(evento.getAgenda()));
		
		Gson gson = new Gson();
		String retorno = gson.toJson(lAgenda);
		
		return Response.status(200).entity(retorno).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		
	}
	
	@Path("getevento")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getEvento(Eventos evento) {
		
		List<Eventos> lEventos = new ArrayList<>();
		lEventos = EventoDAO.get();
		
		return Response.status(200).entity(lEventos).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		
	
	}
	
	//-----------------------------------------------------
	
	
	private Response pGetEvento() {
		Eventos evento = new Eventos();
		return getEvento(evento);
	}
	
	private Response pAddEvento() {
		
		Eventos evento = new Eventos();
		evento.setNome("ANIVERSARIO DE VENANCIO");
		String data = LocalDate.of(2018, 5 , 25).toString();
		evento.setData(data);
		String hora = LocalTime.of(16, 0).toString();
		evento.setHora(hora);
		evento.setImagePath("teste/image/ANIVERSARIO");
		Localizacao l = new Localizacao("-8.017837", "-34.983855");
		evento.setLocalizacao(l);
		evento.setSobre("FESTA DE VENANCIO");
		evento.setAgenda("5a8deb97892f821764717f43");
	
		return this.newEvento(evento);
		
	}
	
	private Response pAddAgenda() {
		
		String localData = LocalDate.now().toString();
		
		AgendaMembro am = new AgendaMembro("5aaace8a35e96f2554871abc", "ADMINISTRADOR", "");
		
		Agenda agenda = new Agenda();
		agenda.setAtivo("true");
		agenda.setDataCriacao(localData);
		agenda.setImagePath("c:/teste/image/agenda2/imagePath");
		List<AgendaMembro> agendaMembros = new ArrayList<>();
		agendaMembros.add(am);
		agenda.setAgendaMembro(agendaMembros);
		agenda.setNome("AGENDA DO CELULAR 1");
		agenda.setSobre("TESTE DE INCLUSAO DE AGENDAS  DO CELULAR 1");
		List<Eventos> eventos = new ArrayList<>();
		agenda.setEventos(eventos);
		
		return this.addAgenda(agenda);
		
	}
	
	private Response pAddmembro() {
		
		Membro membro = new Membro();
		membro.setNumeroCelular("+5581999999991");
		membro.setAtivo(true);
		
		membro.setEmail("membro1@Mmembro1.com"); 
		membro.setImagePath("c:/teste/teste/MEMBRO1.png"); 
		membro.setNome("MEMBRO1");
		
		return addMembro(membro);
		
	}
	

}
