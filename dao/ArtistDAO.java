/**
 * 
 */
package iuh.fit.dao;

import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;

import iuh.fit.entity.Artist;
import iuh.fit.util.AppUtil;

/**
 * @author Trần Nguyên Vũ
 * @version 1.0
 * @created 25 Mar 2024 - 10:10:37 pm
 */
public class ArtistDAO {
	private Driver driver;
	private SessionConfig sessionConfig;

	public ArtistDAO() {
		this.driver = AppUtil.getDriver();
		this.sessionConfig = AppUtil.getSessionConfig();
	}

	public boolean addArtist(Artist artist) {
		String query = "CREATE (ar:Artist {id: $id, name: $name, birthDate: $birthDate, url: $url})";
		Map<String, Object> properties = AppUtil.getArtistsProperties(artist);

		try (Session session = driver.session(sessionConfig)) {
			return session.executeWrite(tx -> {
				Result result = tx.run(query, properties);
				return result.consume().counters().nodesCreated() > 0;
			});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Artist> getArtists() {
		String query = "MATCH (ar:Artist) RETURN ar";

		try (Session session = driver.session()) {
			return session.executeRead(tx -> {
				Result result = tx.run(query);
				return result.list().stream().map(record -> {
					return AppUtil.nodeToArtist(record.get("ar").asNode());
				}).toList();
			});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void close() {
		this.driver.close();
	}
}
