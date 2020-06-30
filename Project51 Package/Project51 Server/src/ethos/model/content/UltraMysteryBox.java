package ethos.model.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.items.GameItem;
import ethos.model.items.ItemAssistant;
import ethos.model.items.ItemDefinition;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;

/**
 * Revamped a simple means of receiving a random item based on chance.
 * 
 * @author Jason MacKeigan
 * @date Oct 29, 2014, 1:43:44 PM
 */
public class UltraMysteryBox {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 13346;

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
				new GameItem(4716),
				new GameItem(4724),
				new GameItem(4745),
				new GameItem(4753),
				new GameItem(4708),
				new GameItem(4732),
				new GameItem(4720),
				new GameItem(4728),
				new GameItem(4749),
				new GameItem(4757),
				new GameItem(4712),
				new GameItem(4736),
				new GameItem(4722),
				new GameItem(4730),
				new GameItem(4751),
				new GameItem(4759),
				new GameItem(4714),
				new GameItem(4738),
				new GameItem(4718),
				new GameItem(4726),
				new GameItem(4747),
				new GameItem(4755),
				new GameItem(4710),
				new GameItem(4734),
				new GameItem(22804, 250),//250 Dragon Knife
				new GameItem(1053),//Green h'ween
				new GameItem(1055),//Blue h'ween
				new GameItem(1057),//red h'ween
				new GameItem(22118),//Wrath Talisman
				new GameItem(21221),//Wrath Tiara
				new GameItem(6083),//Tarn's Diary
				new GameItem(21880, 500),//500 Wrath Runes
				new GameItem(21880, 1000),//1000 Wrath Runes
				new GameItem(537, 500),//500 Noted Dragon Bones
				new GameItem(9194, 1000),//1000 Onyx Bolt Tips
				new GameItem(9245, 500),//500 Onyx Bolts (E)
				new GameItem(9244, 1000),//500 Onyx Bolts (E)
				new GameItem(12829),//Spirit Shield
				new GameItem(12831),//Blessed Spirit Shield
				new GameItem(2572),//Ring Of Wealth
				new GameItem(21847),//Snow Imp Head
				new GameItem(21849),//Snow Imp Body
				new GameItem(21851),//Snow Imp Legs
				new GameItem(21853),//Snow Imp Tail
				new GameItem(21855),//Snow Imp Gloves
				new GameItem(21857),//Snow Imp Feet
				new GameItem(22689),//Clown Mask
				new GameItem(22692),//Clown Bow Tie
				new GameItem(22695),//Clown Gown
				new GameItem(22698),//Clown Trousers
				new GameItem(22701),//Clown shoes
				new GameItem(21857),//Snow Imp Feet
				new GameItem(20756),//Hill Giant Club
				new GameItem(14945),//Bonus XP book (15 minutes)
				new GameItem(6571),//Uncut Onyx
				new GameItem(6914),//Master Wand
				new GameItem(4225),//Crystal Shield
				new GameItem(11838),//Saradomin Sword
				new GameItem(21298),//Obsidian Helmet
				new GameItem(21301),//Obsidian Platebody
				new GameItem(21304),//Obsidian Platelegs
				new GameItem(6731),//Seers Ring
				new GameItem(6733),//Archers Ring
				new GameItem(6735),//Warrior Ring
				new GameItem(6737),//Berserker Ring
				new GameItem(12851),//Amulet of the Damned
				new GameItem(21817),//Bracelet of Etherum
				new GameItem(11840),//Dragon Boots
				new GameItem(4152, 3),//3x Abyssal Whip
				new GameItem(13233, 1),//Smouldering Stone
				new GameItem(2577))//Rangers boots
		);
		
	items.put(Rarity.UNCOMMON,
			Arrays.asList(
					new GameItem(11920),//Dragon Pickaxe
					new GameItem(13227),//Eternal Crystal
					new GameItem(22557),//Amulet of Avarice
					new GameItem(13513),//Bonus Xp Book (30 minutes)
					new GameItem(14947),//Bonus Xp Book (60 minutes)
					new GameItem(13279),//Double Drop Book (15 minutes)
					new GameItem(30256),//Spectre Mask
					new GameItem(30257),//Spectre Top
					new GameItem(30258),//Spectre Gloves
					new GameItem(30259),//Spectre Leggings
					new GameItem(30260),//Spectre Socks
					new GameItem(23091),//Ornate Gloves
					new GameItem(23093),//Ornate Boots
					new GameItem(23095),//Ornate Legs
					new GameItem(23097),//Ornate Top
					new GameItem(23099),//Ornate Cape
					new GameItem(23011),//Ornate Helm
					new GameItem(22111),//Dragonbone Necklace
					new GameItem(30306),//Pink Boxing Gloves
					new GameItem(13343),//black santa hat
					new GameItem(13344),//Inverted Santa Hat
					new GameItem(21034),//Dexterous Prayer Scroll
					new GameItem(21079),//Arcane Prayer Scroll
					new GameItem(12873),//Guthan Set
					new GameItem(12875),//Verac Set
					new GameItem(12877),//Dharok Set
					new GameItem(12879),//Torag Set
					new GameItem(12881),//Ahrim Set
					new GameItem(12883),//Karil Set
					new GameItem(12785),//Ring of Wealth (i)
					new GameItem(1053),//Green h'ween
					new GameItem(1055),//Blue h'ween
					new GameItem(1057),//red h'ween
					new GameItem(11847),//Black H'ween Mask
					new GameItem(1050),//Santa Hat
					new GameItem(19481),//Heavy Ballista	
					new GameItem(13200),//Tanzanite Mutagen
					new GameItem(13201),//Magma Mutagen
					new GameItem(11283),//Dragonfire Shield
					new GameItem(13271),//Abyssal Dagger (p++)
					new GameItem(1038),//red phat
					new GameItem(1040),//yellow phat
					new GameItem(1042),//blue phat
					new GameItem(1044),//green phat
					new GameItem(1046),//purple phat
					new GameItem(1048),//white phat
					new GameItem(12932),//Magic Fang
					new GameItem(20062),//Torture Ornament Kit
					new GameItem(22246),//Anguish Ornament Kit
					new GameItem(22975),//Brimstone Ring
					new GameItem(11824),//Zamorakian Spear
					new GameItem(22296),//Zamorakian Spear
					new GameItem(11832),//Bandos Chestplate
					new GameItem(11830),//Armadyl Chainskirt
					new GameItem(11802),//Armadyl Godsword
					new GameItem(11804),//Bandos Godsword
					new GameItem(11808),//Zamorak Godsword
					new GameItem(11806))//Saradomin Godsword
	);
		
		items.put(Rarity.RARE,
				Arrays.asList(
						new GameItem(21295),//infernal cape
						new GameItem(12922),//Tanzanite Fang
						new GameItem(13229),//Pegasian Crystal
						new GameItem(13231),//Primordial Crystal
						new GameItem(21633),//Anicnet Wyvern Shield
						new GameItem(21126),//Double experience ring
						new GameItem(19529),//zenyte shard
						new GameItem(14951),//Bonus Xp Book (90 minutes)
						new GameItem(14949),//Bonus Xp Book (3 hours)
						new GameItem(14957),//Double Drop book (1 hour)
						new GameItem(14955),//Double Drop book (30 minutes)
						new GameItem(12929),//Serpentine Helm (Uncharged)
						new GameItem(11834),//Bandos Tassets
						new GameItem(11828),//Armadyl Chestplate
						new GameItem(20997),//twisted bow
						new GameItem(22542),//Viggora's Chainmace (u)
						new GameItem(22547),//Craw's bow (u)
						new GameItem(22552),//Thammaron's Sceptre (u)
						new GameItem(22516),//Dawnbringer
						new GameItem(11862),//black phat
						new GameItem(11863),//rainbow phat
						new GameItem(30307),//Blue Santa Hat
						new GameItem(30308),//Purple Santa Hat
						new GameItem(30290),//Purple H'ween Mask
						new GameItem(30291),//Pink H'ween Mask
						new GameItem(30292),//Lime H'ween Mask
						new GameItem(30293),//Orange H'ween Mask
						new GameItem(12399),//partyhat & specs
						new GameItem(20784),//dragon claws
						new GameItem(21859),//Wise Old Man's Santa Hat
						new GameItem(21012),//Dragon Hunter Crossbow
						new GameItem(21003),//Elder Maul
						new GameItem(11785),//Armadyl Crossbow
						new GameItem(21748, 1)));//Noon pet
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new myster box to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public UltraMysteryBox(Player player) {
		this.player = player;
	}

	/**
	 * Can the player open the mystery box
	 */
	//private boolean canMysteryBox = true;
	
	/**
	 * The prize received
	 */
	private int mysteryPrize;

	private int mysteryAmount;
	
	private int spinNum = 0;
	
	/**
	 * The chance to obtain the item
	 */
	private int random;
	
	private final int INTERFACE_ID = 47000;
	private final int ITEM_FRAME = 47101;
	
	/**
	 * The rarity of the reward
	 */
	private Rarity rewardRarity;

	/**
	 * Represents the rarity of a certain list of items
	 */
	enum Rarity {
		UNCOMMON("<col=005eff>"),
		COMMON("<col=336600>"),
		RARE("<col=B80000>");
		
		private String color;
		
		Rarity(String color) {
			this.color = color;
		}
		
		public String getColor() {
			return color;
		}
		
	    public static Rarity forId(int id) {
	        for (Rarity tier : Rarity.values()) {
	            if (tier.ordinal() == id)
	                return tier;
	        }
	        return null;
	    }
	}
	
	public void spin() {
		// Server side checks for spin
		if (!player.canMysteryBox) {
			player.sendMessage("Please finish your current spin.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You require a mystery box to do this.");
			return;
		}

		// Delete box
		player.getItems().deleteItem(MYSTERY_BOX, 1);
		// Initiate spin
		player.sendMessage(":resetBox");
		for (int i=0; i<66; i++){
			player.getPA().mysteryBoxItemOnInterface(-1, 1, ITEM_FRAME, i);
		}
		spinNum = 0;
		player.sendMessage(":spin");
		process();
	}
	
	public void process() {
		// Reset prize
		mysteryPrize = -1;

		mysteryAmount = -1;
		// Can't spin when already in progress
		player.canMysteryBox = false;
		
		random = Misc.random(100);
		List<GameItem> itemList = random < 50 ? items.get(Rarity.COMMON) : random >= 50 && random <= 91 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
		rewardRarity = random < 50 ? Rarity.COMMON : random >= 50 && random <= 91 ? Rarity.UNCOMMON : Rarity.RARE;
		
		GameItem item = Misc.getRandomItem(itemList);

		mysteryPrize = item.getId();

		mysteryAmount = item.getAmount();

		// Send items to interface
		// Move non-prize items client side if you would like to reduce server load
		if (spinNum == 0) {
			for (int i=0; i<66; i++){
				Rarity notPrizeRarity = Rarity.values()[new Random().nextInt(Rarity.values().length)];
				GameItem NotPrize =Misc.getRandomItem(items.get(notPrizeRarity));
				final int NOT_PRIZE_ID = NotPrize.getId();
				final int NOT_PRIZE_AMOUNT = NotPrize.getAmount();
				sendItem(i, 55, mysteryPrize, NOT_PRIZE_ID,1);
			}
		} else {
			for (int i=spinNum*50 + 16; i<spinNum*50 + 66; i++){
				Rarity notPrizeRarity = Rarity.values()[new Random().nextInt(Rarity.values().length)];	
				final int NOT_PRIZE_ID = Misc.getRandomItem(items.get(notPrizeRarity)).getId();
				sendItem(i, (spinNum+1)*50 + 5, mysteryPrize, NOT_PRIZE_ID, mysteryAmount);
			}
		}
		spinNum++;
	}
	
	public void reward() {
		if (mysteryPrize == -1) {
			return;
		}
		
		//player.boxCurrentlyUsing = -1;
		
		player.getItems().addItemUnderAnyCircumstance(mysteryPrize, mysteryAmount);
		
		// Reward text colour
		String tier = rewardRarity.getColor();
		
		// Reward message
		String name = ItemDefinition.forId(mysteryPrize).getName();
		if (name.substring(name.length() - 1).equals("s")) {
			player.sendMessage("Congratulations, you have won " + tier + name + "@bla@!");
		}
		else {
			player.sendMessage("Congratulations, you have won a " + tier + name + "@bla@!");
		}
		
		if (random > 95) {
			PlayerHandler.executeGlobalMessage("[<col=CC0000>Ultra</col>] <col=255>" + Misc.formatPlayerName(player.playerName)
					+ "</col> hit the jackpot and got a <col=CC0000>"+name+"</col> !");
		}
		
		// Can now spin again
		player.canMysteryBox = true;
	}
	
	public void sendItem(int i, int prizeSlot, int PRIZE_ID, int NOT_PRIZE_ID, int amount) {
		if (i == prizeSlot) {
			player.getPA().mysteryBoxItemOnInterface(PRIZE_ID, amount, ITEM_FRAME, i);
		}
		else {
			player.getPA().mysteryBoxItemOnInterface(NOT_PRIZE_ID, amount, ITEM_FRAME, i);
		}
	}
	
	public void openInterface() {
		player.boxCurrentlyUsing = MYSTERY_BOX;
		// Reset interface
		player.sendMessage(":resetBox");
		for (int i=0; i<66; i++){
			player.getPA().mysteryBoxItemOnInterface(-1, 1, ITEM_FRAME, i);
		}
		spinNum = 0;
		// Open
		player.getPA().sendString("Ultra Mystery Box", 47002);
		player.getPA().showInterface(INTERFACE_ID);
	}

}