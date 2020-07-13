package br.com.sgcraft.gladiador.chat;

import org.bukkit.event.player.*;

import br.com.sgcraft.gladiador.mensagens.*;

import org.bukkit.event.*;

public class NormalChat implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onChat(final AsyncPlayerChatEvent e) {
        if (Mensagens.containsVencedor(e.getPlayer().getName())) {
            final String[] s = e.getFormat().split("<");
            e.setFormat(String.valueOf(String.valueOf(s[0])) + "<" + Mensagens.getTag() + s[1]);
        }
    }
}
