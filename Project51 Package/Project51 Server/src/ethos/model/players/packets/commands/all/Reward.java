package ethos.model.players.packets.commands.all;

import java.util.Optional;

import ethos.Config;
import ethos.Server;
import ethos.model.players.Player;
import ethos.model.players.Right;
import ethos.model.players.packets.commands.Command;

/**
 * Opens the vote page in the default web browser.
 *
 * @author Emiel
 */
public class Reward extends Command {

    String lastAuth = "";


    @Override
    public void execute(Player player, String input) {

        String[] args = input.split(" ");
        if (args.length == 1) {
            player.sendMessage("Please use [::reward 1 amount], or [::reward 1 all].");
            player.sendMessage("1 Vote ticket is 1 Vote point.");
            return;
        }
        final String playerName = player.playerName;
        final String id = args[0];
        final String amount = args[1];
        com.everythingrs.vote.Vote.service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    com.everythingrs.vote.Vote[] reward = com.everythingrs.vote.Vote.reward("34yOj45xuz6SAQVXwdpFCdzUQLYjSese9Msetz1YVuKa17g2JcbWVF0obHgn0ViRGlOOIklz",
                            playerName, id, amount);
                    if (reward[0].message != null) {
                        player.sendMessage(reward[0].message);
                        return;
                    }
                    player.getItems().addItemUnderAnyCircumstance(reward[0].reward_id, reward[0].give_amount);
					if (Config.DOUBLE_VOTE_INCENTIVES) {
						player.getItems().addItemUnderAnyCircumstance(reward[0].reward_id, (reward[0].give_amount / 2));
					}
					if (player.summonId == 13071) {
						player.getItems().addItemUnderAnyCircumstance(reward[0].reward_id, (reward[0].give_amount / 2));
						player.sendMessage("You receive 50% more points from your Chompy Chick pet!");
					}
                    player.sendMessage(
                            "@cr10@Thank you for voting! You now have " + reward[0].vote_points + " vote points.");
                } catch (Exception e) {
                    player.sendMessage("Api Services are currently offline. Please check back shortly");
                    e.printStackTrace();
                }
            }

        });
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of("Claims your vote from ::vote");
    }

    @Override
    public Optional<String> getParameter() {
        return Optional.of("id# amount#");
    }
}

