/**
 * 
 */
package test.dao;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import iuh.fit.dao.AlbumDAO;
import iuh.fit.entity.Album;

/**
 * @author Trần Nguyên Vũ
 * @version 1.0
 * @created 26 Mar 2024 - 1:02:10 am
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AlbumDAOTest {
	AlbumDAO albumDAO;

	@BeforeAll
	void init() {
		albumDAO = new AlbumDAO();
	}

	@Test
	void testUpdatePriceOfAlbum() {
		boolean result = albumDAO.updatePriceOfAlbum("1", 100.0);

		Assertions.assertTrue(result);
	}

	@Test
	void testListAlbumByGenre() {
		List<Album> albums = albumDAO.listAlbumByGenre("folk");
		Assertions.assertNotNull(albums);
		Assertions.assertTrue(albums.size() > 0);
	}

	@Test
	void testGetNumberOfAlbumsByGenre() {
		System.out.println(albumDAO.getNumberOfAlbumsByGenre());

		Assertions.assertNotNull(albumDAO.getNumberOfAlbumsByGenre());
	}

	@AfterAll
	void tearDown() {
		albumDAO.close();
		albumDAO = null; // help gc - garbage collector
	}
}
