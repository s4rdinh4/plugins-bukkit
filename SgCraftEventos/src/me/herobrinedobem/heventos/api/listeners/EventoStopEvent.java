package me.herobrinedobem.heventos.api.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import me.herobrinedobem.heventos.api.EventoBase;
import me.herobrinedobem.heventos.utils.EventoCancellType;

public class EventoStopEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private EventoBase evento;
	private EventoCancellType cancellType;
	
	public EventoStopEvent(EventoBase evento, EventoCancellType cancellType) {
		this.evento = evento;
		this.cancellType = cancellType;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
		 
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public EventoBase getEvento() {
		return evento;
	}
	
	public EventoCancellType getCancellType() {
		return cancellType;
	}
	
}
