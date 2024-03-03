package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import javax.swing.JOptionPane;
import org.bson.Document;

@SuppressWarnings({ "resource", "unused" })
public class conectaMongo {

	public void getValues() {
		System.out.println("Método getValues()");
		// Conexão Mongo
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("cogurpg");
		MongoCollection<Document> docs = db.getCollection("cogurpg");
		for (Document doc : docs.find()) {
			System.out.println("item: " + doc);
		}
		System.out.println("getValues() -  ok - Finalizou");

	}

	public void insertValue(String nome, String email, String senha) {
		System.out.println("Método insertValues()");
		// Conexão Mongo
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("cogurpg");
		MongoCollection<Document> docs = db.getCollection("cogurpg");

		Entrada user = createUser(nome, email, senha);
		// Cria um user obj da classe conectar, chamando o metodo createUser() - logo
		// abaixo
		Document doc = createDocument(user);
		// Cria um doc que referencia o conteudo de user do createDocument(), obs: o
		// Banco só entende objetos do tipo Document
		docs.insertOne(doc);// Insere no mongo o conteúdo de doc
		getValues();
		System.out.println("insertValues() - ok");

	}

	public Entrada createUser(String nome, String email, String senha) {
		// Esse metodo deve ser uma entrada externa (interface, scanner ou JOptionPanel
		Entrada entrada = new Entrada();
		entrada.setNome(nome);
		entrada.setEmail(email);
		entrada.setSenha(senha);
		return entrada;
	}

	public Document createDocument(Entrada user) {
		Document docBuilder = new Document();
		docBuilder.append("nome", user.getNome());
		docBuilder.append("email", user.getEmail());
		docBuilder.append("senha", user.getSenha());
		return docBuilder;
	}

	public void findValuesName(String nome) {
		System.out.println("Método findName()");
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("cogurpg");
		MongoCollection<Document> docs = db.getCollection("cogurpg");

		for (Document doc : docs.find(Filters.eq("nome", nome))) {
			System.out.println("Achou pelo nome: " + doc);
		}
		System.out.println("findName() - finalizou");
	}

	public boolean findValuesSignUP(String email) {
		boolean emailJaCadastrado = false;

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("cogurpg");
		MongoCollection<Document> docs = db.getCollection("cogurpg");

		for (Document doc : docs.find(Filters.eq("email", email))) {
			emailJaCadastrado = true;
			break; // Se encontrou um documento com o email, não precisa continuar procurando
		}

		if (emailJaCadastrado == true) {
			JOptionPane.showMessageDialog(null, "Cadastre-se com um email diferente!", "Email já cadastrado!",
					JOptionPane.OK_OPTION);
			return true; // Email já cadastrado
		} else {
			return false; // Email não cadastrado
		}
	}

	public boolean findValuesSignINEmail(String email) {
		boolean emailVerificado = false;

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("cogurpg");
		MongoCollection<Document> docs = db.getCollection("cogurpg");

		for (Document doc : docs.find(Filters.eq("email", email))) {
			emailVerificado = true;
			break; // Se encontrou um documento com o email, não precisa continuar procurando
		}

		if (emailVerificado == true) {
			return true; // Email correto / Encontrado para verificação de email e senha
		} else {
			return false; // Email incorreto
		}
	}

	public boolean findValuesSignINSenha(String senha) {
		boolean senhaVerificada = false;

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("cogurpg");
		MongoCollection<Document> docs = db.getCollection("cogurpg");

		for (Document doc : docs.find(Filters.eq("senha", senha))) {
			senhaVerificada = true;
			break; // Se encontrou um documento com a senha, não precisa continuar procurando
		}

		if (senhaVerificada == true) {
			return true; // Senha correta / Encontrada para verificação de email e senha
		} else {
			return false; // Senha incorreta / Não encontrada
		}
	}

	public void updateValues() {

		System.out.println("updateValues");
		MongoClient mongo = new MongoClient("localhost", 27017);

		MongoDatabase db = mongo.getDatabase("cogurpg");
		MongoCollection<Document> docs = db.getCollection("cogurpg");

		docs.updateOne(Filters.eq("email", "admin@cogurpg.com"), Updates.set("senha", "admin123"));
		System.out.println("Documento teve sucesso no update...");
		for (Document doc : docs.find()) {
			System.out.println("item update: " + doc);
		}

	}

	public void deleteValues() {
		System.out.println("deleteValues");
		MongoClient mongo = new MongoClient("localhost", 27017);

		MongoDatabase db = mongo.getDatabase("cogurpg");
		MongoCollection<Document> docs = db.getCollection("cogurpg");

		docs.deleteOne(Filters.eq("nome", "Marcio Pereira"));
		System.out.println("Documento teve sucesso no delete...");
		for (Document doc : docs.find()) {
			System.out.println("item update: " + doc);
		}

	}
}
