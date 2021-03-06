/*uncomment herepackage ethos.Highscores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ethos.Config;
import ethos.model.players.Player;
import ethos.model.players.skills.Skilling;
 
public class Highscores implements Runnable {
	
	public static void main(String[] args) {
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+HOST+":3306/"+DATABASE, USER, PASS);
			
			Statement stmt = conn.createStatement(1005, 1008);
            stmt.executeUpdate("DELETE FROM highscores WHERE username='test user'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static final String HOST = "162.252.9.68";
	public static final String USER = "playang1_forumadmin";
	public static final String PASS = "FCc4QfvZuy#FV}T?~O";
	public static final String DATABASE = "playang1_hiscores";
	
	private Player player;
	private Connection conn;
	private Statement stmt;
	
	public Highscores(Player player) {
		this.player = player;
	}
	
	public boolean connect() {
		try {
			this.conn = DriverManager.getConnection("jdbc:mysql://"+HOST+":3306/"+DATABASE, USER, PASS);
			System.out.print("We are connected.");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void run() {
		try {
			System.out.print("Started hiscores run\n");
			if (Config.BLOCK_SQL || !connect()) {
				return;
			}
		
			String name = player.getName();
			this.executeUpdate("DELETE FROM highscores WHERE username='"+name+"'");
			
			
			PreparedStatement stmt2 = prepare(generateQuery());
			stmt2.setString(1, player.getName());
			stmt2.setInt(2, player.getRights().getPrimary().getValue());
			stmt2.setLong(3, this.getTotalExp());
			
			for (int i = 0; i < 24; i++) {
				player.sendMessage("Skill: " + i + " = "+ player.playerXP[i]);
				stmt2.setInt(4 + i, (int) player.playerXP[i]);
			}
			
			stmt2.setInt(28, 69);
			
			stmt2.execute();
			
			destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public long getTotalExp() {
		long total = 0;
		for (int exp : player.playerXP) {
			total += exp;
		}
		return total;
	}
	
	public int getTotalLevel() {
		int total = 0;
		for (int exp : player.playerXP) {
			total += player.getLevelForXP(exp);
		}
		return total;
	}
	
	public PreparedStatement prepare(String query) throws SQLException {
		return conn.prepareStatement(query);
	}
	
	public void destroy() {
        try {
    		conn.close();
        	conn = null;
        	if (stmt != null) {
    			stmt.close();
        		stmt = null;
        	}
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

	public int executeUpdate(String query) {
        try {
        	this.stmt = this.conn.createStatement(1005, 1008);
            int results = stmt.executeUpdate(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

	public ResultSet executeQuery(String query) {
        try {
        	this.stmt = this.conn.createStatement(1005, 1008);
            ResultSet results = stmt.executeQuery(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
	public static String generateQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO hs_users (");
		sb.append("username, ");
		sb.append("rights, ");
		sb.append("mode, ");
		sb.append("total_level, ");
		sb.append("overall_xp, ");
		sb.append("attack_xp, ");
		sb.append("defence_xp, ");
		sb.append("strength_xp, ");
		sb.append("constitution_xp, ");
		sb.append("ranged_xp, ");
		sb.append("prayer_xp, ");
		sb.append("magic_xp, ");
		sb.append("cooking_xp, ");
		sb.append("woodcutting_xp, ");
		sb.append("fletching_xp, ");
		sb.append("fishing_xp, ");
		sb.append("firemaking_xp, ");
		sb.append("crafting_xp, ");
		sb.append("smithing_xp, ");
		sb.append("mining_xp, ");
		sb.append("herblore_xp, ");
		sb.append("agility_xp, ");
		sb.append("thieving_xp, ");
		sb.append("slayer_xp, ");
		sb.append("farming_xp, ");
		sb.append("runecrafting_xp, ");
		sb.append("hunter_xp, ");
		sb.append("construction_xp, ");
		sb.append("summoning_xp, ");
		sb.append("dungeoneering_xp) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		return sb.toString();
	}
	/*public static String generateQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO highscores (");
		sb.append("username, ");
		sb.append("rights, ");
		sb.append("overall_xp, ");
		sb.append("attack_xp, ");
		sb.append("defence_xp, ");
		sb.append("strength_xp, ");
		sb.append("constitution_xp, ");
		sb.append("ranged_xp, ");
		sb.append("prayer_xp, ");
		sb.append("magic_xp, ");
		sb.append("cooking_xp, ");
		sb.append("woodcutting_xp, ");
		sb.append("fletching_xp, ");
		sb.append("fishing_xp, ");
		sb.append("firemaking_xp, ");
		sb.append("crafting_xp, ");
		sb.append("smithing_xp, ");
		sb.append("mining_xp, ");
		sb.append("herblore_xp, ");
		sb.append("agility_xp, ");
		sb.append("thieving_xp, ");
		sb.append("slayer_xp, ");
		sb.append("farming_xp, ");
		sb.append("runecrafting_xp, ");
		sb.append("hunter_xp, ");
		sb.append("construction_xp) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		return sb.toString();
	}*/
	
/*uncomment here}*/