package unl.cse.games;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import unl.cse.games.DatabaseInfo;

public class GamesDatabaseUtils {

	/**
	 * Adds an availability record to the availability table for the given <code>gameId</code>
	 * and <code>platformId</code> along with the given <code>publishYear</code>.  This models
	 * the fact that a certain game is available on a certain platform.
	 * 
	 * @param gameId
	 * @param platformId
	 * @param publishYear
	 */
	public static void addAvailability(Integer gameId, Integer platformId, Integer publishYear) {
		
		String query = "INSERT INTO availability (game_id,platform_id,publish_year) VALUES (?, ?, ?)";
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, gameId);
			ps.setInt(2, platformId);
			ps.setInt(3, publishYear);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(ps != null && !ps.isClosed())
				ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		closeConnection(conn);
		return;		
	}

	/**
	 * This method adds a new publisher record to the publisher table with
	 * the given <code>name</code>
	 * 
	 * @param name
	 */
	public static void addPublisher(String name) {
		
		//unprepared statement intentionally done so that 
		//you can sanitize it in the advanced activity
		//you would NEVER do something like this in real life
		String query = "INSERT INTO publisher (name) VALUES ('"+name+"')";
		
		Connection conn = getConnection();
		Statement s = null;
		try {
			s = conn.createStatement();
			s.executeUpdate(query);
			s.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(s != null && !s.isClosed())
				s.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		closeConnection(conn);
		return;
	}
	
	/**
	 * This method adds a new platform record to the platform table
	 * with the given <code>name</code>
	 * 
	 * @param name
	 */
	public static void addPlatform(String name) {
		
		String query = "INSERT INTO platform (name) VALUES (?);";
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, name);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(ps != null && !ps.isClosed())
				ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		closeConnection(conn);
		return;
	}

	/**
	 * This method adds a new video game record to the game table with the
	 * given <code>name</code> (title) and publisher represented by their
	 * <code>publisherId</code>
	 * 
	 * @param name
	 * @param publisherId
	 */
	public static void addVideoGame(String name, Integer publisherId) {
		
		String query = "INSERT INTO game (name,publisher_id) VALUES (?,?)";
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, publisherId);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(ps != null && !ps.isClosed())
				ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		closeConnection(conn);
		return;
	}
	
	/**
	 * This method queries all video game data from the
	 * database and returns a <code>List</code> of 
	 * {@link #VideoGame} object instances.
	 * @return
	 */
	public static List<VideoGame> getAllGames() {
		
		String query = "SELECT g.game_id, g.name, p.publisher_id, p.name FROM game g JOIN publisher p ON g.publisher_id = p.publisher_id";
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<VideoGame> games = new ArrayList<VideoGame>();
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				Publisher p = new Publisher(rs.getInt("p.publisher_id"), rs.getString("p.name"));
				VideoGame g = new VideoGame(rs.getInt("g.game_id"), rs.getString("g.name"), p);
				games.add(g);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		closeConnection(conn);
		for(VideoGame g : games) {
			addAvailability(g);
		}
		return games;	
	}
	
	/**
	 * This method queries the database for video game data for a
	 * particular video game represented by the given <code>name</code>
	 * (title of the game).
	 * 
	 * @param name
	 * @return
	 */
	public static VideoGame getGame(String name) {
		
		String query = "SELECT g.game_id, g.name, p.publisher_id, p.name FROM game g JOIN publisher p ON g.publisher_id = p.publisher_id WHERE g.name = ?";
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		VideoGame g = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, name);
			rs = ps.executeQuery();
			if(rs.next()) {
				Publisher p = new Publisher(rs.getInt("p.publisher_id"), rs.getString("p.name"));
				g = new VideoGame(rs.getInt("g.game_id"), rs.getString("g.name"), p);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		closeConnection(conn);
		addAvailability(g);
		return g;
	}

	private static void addAvailability(VideoGame g) {

		if(g == null) { return; }
		String query = "SELECT p.platform_id AS platform_id, p.name AS name, a.publish_year AS publish_year FROM availability a JOIN platform p ON a.platform_id = p.platform_id WHERE a.game_id = ?";
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Platform p = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, g.getId());
			rs = ps.executeQuery();
			while(rs.next()) {
				p = new Platform(rs.getInt("platform_id"), rs.getString("name"));
				g.addPlatform(p, rs.getInt("publish_year"));
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		closeConnection(conn);

		return;
	}
	
	public static Platform getPlatform(String name) {
		
		String query = "SELECT platform_id, name FROM platform WHERE name = ?";
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Platform p = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, name);
			rs = ps.executeQuery();
			if(rs.next()) {
				p = new Platform(rs.getInt("platform_id"), rs.getString("name"));
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		closeConnection(conn);
		return p;
	}

	public static Publisher getPublisher(String name) {

		String query = "SELECT publisher_id, name FROM publisher WHERE name = ?";
		
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Publisher p = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, name);
			rs = ps.executeQuery();
			if(rs.next()) {
				p = new Publisher(rs.getInt("publisher_id"), rs.getString("name"));
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		closeConnection(conn);
		return p;
	}
	
	private static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return conn;
	}
	
	private static void closeConnection(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
