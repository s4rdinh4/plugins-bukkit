package me.herobrinedobem.heventos.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.herobrinedobem.heventos.HEventos;
import me.herobrinedobem.heventos.api.EventoBaseListener;
import me.herobrinedobem.heventos.api.listeners.EventoPlayerLoseEvent;

public class KillerListener extends EventoBaseListener {

	@Override
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		if (HEventos.getHEventos().getEventosController().getEvento() != null) {
			if (HEventos.getHEventos().getEventosController().getEvento().getParticipantes().contains(e.getPlayer().getName())) {
				if (HEventos.getHEventos().getEventosController().getEvento().isAberto() == false) {
					e.getPlayer().setHealth(0);
				}
				for (String s : HEventos.getHEventos().getEventosController().getEvento().getParticipantes()) {
					Player p = HEventos.getHEventos().getServer().getPlayer(s);
					p.sendMessage(HEventos.getHEventos().getConfigUtil().getMsgDesconect().replace("$player$", e.getPlayer().getName()));
				}
				EventoPlayerLoseEvent event = new EventoPlayerLoseEvent(e.getPlayer(), HEventos.getHEventos().getEventosController().getEvento());
				HEventos.getHEventos().getServer().getPluginManager().callEvent(event);
			}
			if (HEventos.getHEventos().getEventosController().getEvento().isAssistirAtivado()) {
				if (HEventos.getHEventos().getEventosController().getEvento().getCamarotePlayers().contains(e.getPlayer().getName())) {
					HEventos.getHEventos().getEventosController().getEvento().getCamarotePlayers().remove(e.getPlayer().getName());
					e.getPlayer().teleport(HEventos.getHEventos().getEventosController().getEvento().getSaida());
				}
			}
		}
	}

	@EventHandler
	public void onPotionSplashEvent(PotionSplashEvent e) {
		if (HEventos.getHEventos().getEventosController().getEvento() != null) {
			if (e.getPotion().getShooter() instanceof Player) {
				Player p = (Player) e.getPotion().getShooter();
				if (HEventos.getHEventos().getEventosController().getEvento().getCamarotePlayers().contains(p.getName())) {
					e.setCancelled(true);
				}
			}
		}
	}

}
