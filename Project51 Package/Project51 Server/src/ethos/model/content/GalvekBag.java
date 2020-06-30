package ethos.model.content;

import java.util.*;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.items.GameItem;
import ethos.model.items.ItemAssistant;
import ethos.util.Misc;

/**
 * Revamped a simple means of receiving a random item based on chance.
 * 
 * @author Robbie
 *
 */
public class GalvekBag extends CycleEvent {

	/**
	 * The item id of the Pursuit Crate required to trigger the event
	 */
	public static final int GALVEK_BAG = 23071;

	/**
	 * A map containing a List of {@link GameItem}'s that contain items relevant to their rarity.
	 */
	private static Map<Rarity, List<GameItem>> items = new HashMap<>();

	/**
	 * Stores an array of items into each map with the corresponding rarity to the list
	 */
	static {
		items.put(Rarity.COMMON, 
			Arrays.asList(
					new GameItem(565, 1000),//blood runes
					new GameItem(12746),//mysterious emblem
					new GameItem(560, 1000), //death runes
					new GameItem(11937, 150),//note dark crabs
					new GameItem(9242, 200), //ruby bolts (e)
					new GameItem(9244, 200),//dragonstone bolts (e)
					new GameItem(19677), //ancient shard
					new GameItem(4153), //granite maul
					new GameItem(4087), //dragon platelegs
					new GameItem(1128, 5), //noted rune platebody
					new GameItem(1080, 5),//noted rune platelegs
					new GameItem(4588, 5),//note dragon scimitar
					new GameItem(11730),//overload potion
					new GameItem(12696, 15), //noted super combat potion
					new GameItem(990, 3))//noted crystal keys
					
			);
			
		items.put(Rarity.UNCOMMON,
				Arrays.asList(
						new GameItem(11818),//godsword shard 1
						new GameItem(12748),//mysterious emblem (tier 2)
						new GameItem(11840), //dragon boots
						new GameItem(11820),//godsword shard 2 
						new GameItem(11822), //godsword shard 3
						new GameItem(4151), //abyssal whip
						new GameItem(6571), //uncut onyx
						new GameItem(12829),//banshee robe bottom
						new GameItem(20838),//corrupted helm
						new GameItem(20840),//corrupted platebody
						new GameItem(20842),//corrupted platelegs
						new GameItem(20844), //corrupted plateskirt		
						new GameItem(20846),//corrupted kiteshield
						new GameItem(21257), //Slayer's enchantment
						new GameItem(4170), //Slayer Staff
						new GameItem(4708), //Ahrim's hood
						new GameItem(4710), //Ahrim's Staff
						new GameItem(4712), //Ahrim's Robetop
						new GameItem(4714), //Ahrim's Robeskirt
						new GameItem(4716), //Dharok's helm
						new GameItem(4718), //Dharok's Gretaxe
						new GameItem(4720), //Dharok's Platebody
						new GameItem(4722), //Dharok's Platelegs
						new GameItem(4724), //Guthan's helm
						new GameItem(4726), //Guthan's Spear
						new GameItem(4728), //Guthan's Platebody
						new GameItem(4730), //Guthan's Chainskirt					
						new GameItem(4732), //Karil's Coif
						new GameItem(4734), //Karil's Crossbow
						new GameItem(4736), //Karil's Leatherbody
						new GameItem(4738), //Karil's Leathskirt						
						new GameItem(4745), //Torag's Helm
						new GameItem(4747), //Torag's Hammers
						new GameItem(4749), //Torag's Platebody
						new GameItem(4751), //Torag's Platelegs						
						new GameItem(4753), //Verac's Helm
						new GameItem(4755), //Verac's flail
						new GameItem(4757), //Verac's body
						new GameItem(4759), //Verac's skirt
						new GameItem(12875), //veracs armour set
						new GameItem(12877), //dharoks armour set
						new GameItem(12879), //torag's armour set
						new GameItem(12881), //ahrim's armour set
						new GameItem(12883), //karil's armour set
						new GameItem(22804, 150), //Dragon knife
						new GameItem(995, 5000000), //7.5m coins
						new GameItem(12696, 35), //noted super combat potion
						new GameItem(13440, 450), //noted raw anglerfish
						new GameItem(386, 2500), //noted sharks
						new GameItem(11257, 5), //noted dragon impling jar
						new GameItem(19732, 2), //lucky impling jar
						new GameItem(20773), //banshee mask
						new GameItem(19685),//dark totem
						new GameItem(20775),//banshee robe top
						new GameItem(6816, 60),//noted wyvern bones
						new GameItem(20777))//banshee robe bottom
						
		);
			
			items.put(Rarity.RARE,
					Arrays.asList(
							new GameItem(13346),//21295
							new GameItem(6199),//21295
							new GameItem(12831),//Blessed Spirit Shield
							new GameItem(12749),//mysterious emblem tier 3
							new GameItem(22804, 500), //Dragon knife
							new GameItem(22689), //clown mask
							new GameItem(22692), //clown bow tie
							new GameItem(22695), //clown gown
							new GameItem(22698), //clown trousers
							new GameItem(22701), //clown shoes
							new GameItem(4151),//abyssal whip
							new GameItem(6571),//uncut onyx
							new GameItem(13346),//ultra mystery box
							new GameItem(19677, 3),//Ancient shards
							new GameItem(11230, Misc.random(200) + 50),//Dragon darts
							new GameItem(995, 1000000)));	//10m coins
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new bag to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public GalvekBag(Player player) {
		this.player = player;
	}

	/**
	 * Opens a PvM Casket if possible, and ultimately triggers and event, if possible.
	 */
	public void open() {
		if (System.currentTimeMillis() - player.lastMysteryBox < 150 * 4) {
			return;
		}
		if (player.getItems().freeSlots() < 2) {
			player.sendMessage("You need atleast two free slots to open an ancient loot bag.");
			return;
		}
		if (!player.getItems().playerHasItem(GALVEK_BAG)) {
			player.sendMessage("You need an ancient loot bag to do this.");
			return;
		}
		player.getItems().deleteItem(GALVEK_BAG, 1);
		player.lastMysteryBox = System.currentTimeMillis();
		CycleEventHandler.getSingleton().stopEvents(this);
		CycleEventHandler.getSingleton().addEvent(this, this, 2);
	}

	/**
	 * Executes the event for receiving the mystery box
	 */
	@SuppressWarnings("unused")
	@Override
	public void execute(CycleEventContainer container) {
		if (player.disconnected || Objects.isNull(player)) {
			container.stop();
			return;
		}
		int coins = 50000 + Misc.random(50000);
		int coinsDouble = 100000 + Misc.random(100000);
		int random = Misc.random(100);
		int rights = player.getRights().getPrimary().getValue() - 1;
		List<GameItem> itemList = random < 55 ? items.get(Rarity.COMMON) : random >= 55 && random <= 90 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
		GameItem item = Misc.getRandomItem(itemList);
		GameItem itemDouble = Misc.getRandomItem(itemList);
		
		if (Misc.random(500) == 1) {
			PlayerHandler.executeGlobalMessage("[<col=CC0000>Ancient Bag</col>] <img="+ rights +"> <col=255>"+ Misc.capitalizeJustFirst(player.playerName)
					+ "</col> hit the jackpot and got an <col=CC0000>Infernal Cape</col>!");
			switch(Misc.random(2)) {
			case 0:
				player.getItems().addItemUnderAnyCircumstance(21295, 1);
				break;
			case 1:
				player.getItems().addItemUnderAnyCircumstance(21295, 1);
				break;
			case 2:
				player.getItems().addItemUnderAnyCircumstance(21295, 1);
				break;
			}
		}

		if (Misc.random(500) == 1) {
			PlayerHandler.executeGlobalMessage("[<col=CC0000>Ancient Bag</col>] <img="+ rights +"> <col=255>"+ Misc.capitalizeJustFirst(player.playerName)
					+ "</col> hit the jackpot and got a <col=CC0000>Dawnbringer</col>!");
			switch(Misc.random(2)) {
			case 0:
				player.getItems().addItemUnderAnyCircumstance(22516, 1);
				break;
			case 1:
				player.getItems().addItemUnderAnyCircumstance(22516, 1);
				break;
			case 2:
				player.getItems().addItemUnderAnyCircumstance(22516, 1);
				break;
			}
		}
		if (Misc.random(1250) == 1) {
			PlayerHandler.executeGlobalMessage("[<col=CC0000>Ancient Bag</col>] <img="+ rights +"> <col=255>"+ Misc.capitalizeJustFirst(player.playerName)
					+ "</col> hit the jackpot and got a <col=CC0000>Ghrazi Rapier</col>!");
			switch(Misc.random(2)) {
			case 0:
				player.getItems().addItemUnderAnyCircumstance(22324, 1);
				break;
			case 1:
				player.getItems().addItemUnderAnyCircumstance(22324, 1);
				break;
			case 2:
				player.getItems().addItemUnderAnyCircumstance(22324, 1);
				break;
			}
		}

		if (Misc.random(10) == 0) {
			player.getItems().addItem(995, coins + coinsDouble);
			player.getItems().addItem(item.getId(), item.getAmount());
			//player.getItems().addItem(itemDouble.getId(), itemDouble.getAmount());
			player.sendMessage("@red@You receive double coins!");
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>gp.");
			/*player.sendMessage("You receive <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>GP.");
			PlayerHandler.executeGlobalMessage("<img=10>" + Misc.formatPlayerName(player.playerName) + " just got very lucky and hit double coins!");
			PlayerHandler.executeGlobalMessage("<img=10>" + Misc.formatPlayerName(player.playerName) + " has received <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId())
					+ "</col> and <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col> from a Pursuit Crate.");*/
		} else {
			player.getItems().addItem(995, coins);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>GP.");
			//PlayerHandler.executeGlobalMessage(
			//		"<img=10>" + Misc.formatPlayerName(player.playerName) + " has received <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col> from a PvM Casket.");
		}
		container.stop();
	}

	/**
	 * Represents the rarity of a certain list of items
	 */
	enum Rarity {
		UNCOMMON, COMMON, RARE
	}

}