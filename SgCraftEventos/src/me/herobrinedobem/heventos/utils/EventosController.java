package me.herobrinedobem.heventos.utils;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import me.herobrinedobem.heventos.HEventos;
import me.herobrinedobem.heventos.api.EventoBase;
import me.herobrinedobem.heventos.api.EventoType;
import me.herobrinedobem.heventos.eventos.BatataQuente;
import me.herobrinedobem.heventos.eventos.BowSpleef;
import me.herobrinedobem.heventos.eventos.EventoNormal;
import me.herobrinedobem.heventos.eventos.Killer;
import me.herobrinedobem.heventos.eventos.MinaMortal;
import me.herobrinedobem.heventos.eventos.Paintball;
import me.herobrinedobem.heventos.eventos.Semaforo;
import me.herobrinedobem.heventos.eventos.Spleef;

public class EventosController {

	private EventoBase evento = null;
	private final HEventos instance;

	public EventosController(final HEventos instance) {
		this.instance = instance;
	}

	public void setEvento(final String name, final EventoType type) {
		switch (type) {
			case SPLEEF:
				this.evento = new Spleef(this.getConfigFile(name));
				break;
			case BOW_SPLEEF:
				this.evento = new BowSpleef(this.getConfigFile(name));
				break;
			case BATATA_QUENTE:
				this.evento = new BatataQuente(this.getConfigFile(name));
				break;
			case KILLER:
				this.evento = new Killer(this.getConfigFile(name));
				break;
			case MINA_MORTAL:
				this.evento = new MinaMortal(this.getConfigFile(name));
				break;
			case PAINTBALL:
				this.evento = new Paintball(this.getConfigFile(name));
				break;
			case SEMAFORO:
				this.evento = new Semaforo(this.getConfigFile(name));
				break;
			case NORMAL:
				this.evento = new EventoNormal(this.getConfigFile(name));
				break;
			default:
				this.evento = new EventoNormal(this.getConfigFile(name));
				break;
		}
	}
	
	public boolean externalEvento(String name){
		boolean contains = false;
		for(EventoBase e : HEventos.getHEventos().getExternalEventos()){
			if(e.getNome().equalsIgnoreCase(name)){
				e.externalPluginStart();
				contains = true;
			}
		}
		if (contains) {
			return true;
		}else{
			return false;
		}
	}
	
	public YamlConfiguration getConfigFile(final String eventoname) {
		final File fileEvento = new File(this.instance.getDataFolder().getAbsolutePath() + "/Eventos/" + eventoname + ".yml");
		return YamlConfiguration.loadConfiguration(fileEvento);
	}

	public EventoBase loadEvento(final String eventoname) {
		final File fileEvento = new File(this.instance.getDataFolder().getAbsolutePath() + "/Eventos/" + eventoname + ".yml");
		final YamlConfiguration configEvento = YamlConfiguration.loadConfiguration(fileEvento);
		return new EventoBase(configEvento);
	}

	public boolean hasEvento(final String evento) {
		try {
			return new File(this.instance.getDataFolder().getAbsolutePath() + "/Eventos/" + evento + ".yml").exists();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public EventoBase getEvento() {
		return this.evento;
	}

	public void setEvento(final EventoBase evento) {
		this.evento = evento;
	}

}
