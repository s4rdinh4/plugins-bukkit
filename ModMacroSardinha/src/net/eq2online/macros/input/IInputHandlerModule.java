package net.eq2online.macros.input;

import bsu;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import net.eq2online.macros.interfaces.ISaveSettings;
import net.eq2online.macros.interfaces.ISettingsProvider;

public interface IInputHandlerModule extends ISaveSettings {
  void initialise(InputHandler paramInputHandler);
  
  void register(bsu parambsu, ISettingsProvider paramISettingsProvider);
  
  void update(ArrayList<InputHandler.KeyEvent> paramArrayList, boolean paramBoolean1, boolean paramBoolean2);
  
  boolean onTick(ArrayList<InputHandler.KeyEvent> paramArrayList);
  
  boolean injectEvents(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2, ByteBuffer paramByteBuffer3, ByteBuffer paramByteBuffer4);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\input\IInputHandlerModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */