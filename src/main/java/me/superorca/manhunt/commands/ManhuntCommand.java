package me.superorca.manhunt.commands;

import me.superorca.manhunt.Profile;
import me.superorca.manhunt.Role;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.EntitySelector;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static me.superorca.manhunt.Utils.F;

@Command("manhunt")
@CommandPermission("manhunt.use")
public class ManhuntCommand {
    private static final List<Profile> players = new ArrayList<>();

    public static List<Profile> getPlayers() {
        return players;
    }

    public static List<Profile> getSpeedrunners() {
        return players.stream().filter(profile -> profile.getRole() == Role.SPEEDRUNNER).collect(Collectors.toList());
    }

    public static List<Profile> getHunters() {
        return players.stream().filter(profile -> profile.getRole() == Role.HUNTER).collect(Collectors.toList());
    }

    @Default
    public void manhunt(final CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(F("&8• &a/manhunt speedrunners &7<add|remove|list> [players]"));
        sender.sendMessage(F("&8• &a/manhunt hunters &7<add|remove|list> [players]"));
        sender.sendMessage(F("&8• &a/manhunt reset"));
        sender.sendMessage("");
    }

    @Subcommand("speedrunners")
    @AutoComplete("add|remove|list *")
    public void speedrunners(final CommandSender sender, final @Default("list") String mode, final @Optional EntitySelector<Player> targets) {
        if ("list".equals(mode)) {
            if (getSpeedrunners().size() < 1) {
                sender.sendMessage(F("&cThere are no speedrunners currently."));
                return;
            }

            sender.sendMessage(F("&fSpeedrunners: &a%s", getSpeedrunners().stream().map(profile -> profile.getPlayer().getName()).collect(Collectors.joining(", "))));
        } else if ("add".equals(mode)) {
            if (targets == null) {
                sender.sendMessage(F("&cYou must specify a value for the player!"));
                return;
            }

            for (Player target : targets) {
                Profile profile = Profile.get(target);

                if (profile.getRole() != null) {
                    sender.sendMessage(F("&a%s &falready has a role."));
                    return;
                }

                profile.setRole(Role.SPEEDRUNNER);
                players.add(profile);
                target.sendMessage(F("&fYou are now a &aspeedrunner&f."));
            }

            sender.sendMessage(F("&a%s &fhave been added to the speedrunnners.", targets.stream().map(HumanEntity::getName).collect(Collectors.joining(", "))));
        } else if ("remove".equals(mode)) {
            if (targets == null) {
                sender.sendMessage(F("&cYou must specify a value for the player!"));
                return;
            }

            for (Player target : targets) {
                Profile profile = Profile.get(target);

                if (profile.getRole() != Role.SPEEDRUNNER) {
                    sender.sendMessage(F("&a%s &fis not a speedrunner.", target.getName()));
                    return;
                }

                profile.setRole(null);
                players.remove(profile);
                target.sendMessage(F("&fYou are no longer a &aspeedrunner&f."));
            }

            sender.sendMessage(F("&a%s &fhave been removed from the speedrunnners.", targets.stream().map(HumanEntity::getName).collect(Collectors.joining(", "))));
        }
    }

    @Subcommand("hunters")
    @AutoComplete("add|remove|list *")
    public void hunters(final CommandSender sender, final @Default("list") String mode, final @Optional EntitySelector<Player> targets) {
        if ("list".equals(mode)) {
            if (getHunters().size() < 1) {
                sender.sendMessage(F("&cThere are no hunters currently."));
                return;
            }

            sender.sendMessage(F("&fHunters: &a%s", getHunters().stream().map(profile -> profile.getPlayer().getName()).collect(Collectors.joining(", "))));
        } else if ("add".equals(mode)) {
            if (targets == null) {
                sender.sendMessage(F("&cYou must specify a value for the player!"));
                return;
            }

            for (Player target : targets) {
                Profile profile = Profile.get(target);

                if (profile.getRole() != null) {
                    sender.sendMessage(F("&a%s &falready has a role."));
                    return;
                }

                profile.setRole(Role.HUNTER);
                players.add(profile);
                target.sendMessage(F("&fYou are now a &ahunter&f."));
            }

            sender.sendMessage(F("&a%s &fhave been added to the speedrunnners.", targets.stream().map(HumanEntity::getName).collect(Collectors.joining(", "))));
        } else if ("remove".equals(mode)) {
            if (targets == null) {
                sender.sendMessage(F("&cYou must specify a value for the player!"));
                return;
            }

            for (Player target : targets) {
                Profile profile = Profile.get(target);

                if (profile.getRole() != Role.HUNTER) {
                    sender.sendMessage(F("&a%s &fis not a hunter.", target.getName()));
                    return;
                }

                profile.setRole(null);
                players.remove(profile);
                target.sendMessage(F("&fYou are no longer a &ahunter&f."));
            }

            sender.sendMessage(F("&a%s &fhave been removed from the hunters.", targets.stream().map(HumanEntity::getName).collect(Collectors.joining(", "))));
        }
    }

    @Subcommand("reset")
    public void reset(final CommandSender sender) {
        for (Profile profile : getPlayers()) {
            profile.setRole(null);
            profile.setTracking(null);
        }

        players.clear();
        sender.sendMessage(F("&fManhunt has been reset."));
    }
}
