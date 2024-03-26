/**
 * 
 */
package iuh.fit.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.types.Node;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import iuh.fit.entity.Artist;

/**
 * @author Trần Nguyên Vũ
 * @version 1.0
 * @created 25 Mar 2024 - 10:03:12 pm
 */
public class AppUtil {

	private static final Gson GSON = new GsonBuilder().create();

	public static Driver getDriver() {
		return GraphDatabase.driver("neo4j://localhost:7687/", AuthTokens.basic("neo4j", "12345678"));
	}

	public static <T> T nodeToPOJO(Node node, Class<T> clazz) { // Generic method

		Map<String, Object> properties = node.asMap(); // HashMap<String, Object>

		String json = GSON.toJson(properties);

		return GSON.fromJson(json, clazz);
	}

	public static <T> Map<String, Object> getProperties(T t) {

		String json = GSON.toJson(t);

		return GSON.fromJson(json, new TypeToken<Map<String, Object>>() {
		});
	}

	// public static <T> Map<String, Object> getArtistsProperties(T t) {
	// 	// TypeAdapter
	// 	TypeAdapter<LocalDate> adapter = new TypeAdapter<LocalDate>() {

	// 		@Override
	// 		public void write(JsonWriter out, LocalDate value) throws IOException {
	// 			out.value(formatDate(value));
	// 		}

	// 		@Override
	// 		public LocalDate read(JsonReader in) throws IOException {
	// 			return parseDate(in.nextString());
	// 		}

	// 	};

	// 	Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, adapter).create();

	// 	String json = gson.toJson(t);
	// 	return gson.fromJson(json, new TypeToken<Map<String, Object>>() {
	// 	});
	// }

	public static String formatDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return date.format(formatter);
	}

	public static LocalDate parseDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(date, formatter);
	}

	// public static Artist nodeToArtist(Node node) {
	// 	Map<String, Object> properties = node.asMap();

	// 	TypeAdapter<LocalDate> adapter = new TypeAdapter<LocalDate>() {

	// 		@Override
	// 		public void write(JsonWriter out, LocalDate value) throws IOException {
	// 			out.value(formatDate(value));

	// 		}

	// 		@Override
	// 		public LocalDate read(JsonReader in) throws IOException {
	// 			return parseDate(in.nextString());
	// 		}

	// 	};

	// 	Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, adapter).create();

	// 	String json = gson.toJson(properties);
	// 	System.out.println(json);
	// 	return gson.fromJson(json, Artist.class);
	// }

//	public static Customer nodeToCustomer(Node node) {
//		Map<String, Object> properties = node.asMap();
//
//		String json = GSON.toJson(properties);
//		System.out.println(json);
//		Address address = GSON.fromJson(json, Address.class);
//
//		Contact contact = GSON.fromJson(json, Contact.class);
//
//		Customer customer = GSON.fromJson(json, Customer.class);
//		customer.setAddress(address);
//		customer.setContact(contact);
//
//		return customer;
//	}
//
//	public static Order nodeToOrder(Node node) {
//
//		String orderID = node.get("orderID").toString().replace("\"", "");
//		String rds = node.get("requiredDate").toString().replace("\"", "").substring(0, 10);
//		String ods = node.get("orderDate").toString().replace("\"", "").substring(0, 10);
//
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//		LocalDate requiredDate = LocalDate.parse(rds, formatter);
//		LocalDate orderDate = LocalDate.parse(ods, formatter);
//
//		return new Order(orderID, orderDate, requiredDate);
//	}

}
