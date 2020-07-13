/*     */ package net.eq2online.macros.scripting.variable.providers;
/*     */ import amj;
/*     */ import aqu;
/*     */ import bsu;
/*     */ import bvt;
/*     */ import bvw;
/*     */ import bvx;
/*     */ import bvy;
/*     */ import bwa;
/*     */ import bwb;
/*     */ import bwd;
/*     */ import bwf;
/*     */ import bwg;
/*     */ import bwj;
/*     */ import bwl;
/*     */ import bwm;
/*     */ import bwn;
/*     */ import bwo;
/*     */ import bwp;
/*     */ import bwq;
/*     */ import bwr;
/*     */ import bws;
/*     */ import bwv;
/*     */ import bwx;
/*     */ import bwy;
/*     */ import bwz;
/*     */ import bxf;
/*     */ import byw;
/*     */ import byx;
/*     */ import byz;
/*     */ import bzc;
/*     */ import bzf;
/*     */ import bzg;
/*     */ import bzh;
/*     */ import bzi;
/*     */ import bzj;
/*     */ import bzk;
/*     */ import bzm;
/*     */ import bzp;
/*     */ import bzx;
/*     */ import cah;
/*     */ import cio;
/*     */ import dt;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.scripting.variable.BlockPropertyTracker;
/*     */ import net.eq2online.macros.scripting.variable.IVariableStore;
/*     */ import qw;
/*     */ import vu;
/*     */ 
/*     */ public class VariableProviderPlayer extends VariableCache {
/*  53 */   private static final HashMap<Class<? extends bxf>, String> guiClassNameMap = new HashMap<Class<? extends bxf>, String>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static final String[] BLOCK_SIDES = new String[] { "B", "T", "N", "S", "W", "E" };
/*     */   
/*  61 */   private static final String[] SIGN_LINES_EMPTY = new String[] { "", "", "", "" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float prevRotationYaw;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  73 */     guiClassNameMap.put(bxf.class, "UNKNOWN");
/*     */ 
/*     */     
/*  76 */     guiClassNameMap.put(bxu.class, "GUIACHIEVEMENTS");
/*  77 */     guiClassNameMap.put(bxv.class, "GUISTATS");
/*  78 */     guiClassNameMap.put(bvx.class, "GUICHAT");
/*  79 */     guiClassNameMap.put(byv.class, "GUICOMMANDBLOCK");
/*  80 */     guiClassNameMap.put(bvy.class, "GUICONFIRMOPENLINK");
/*  81 */     guiClassNameMap.put(byj.class, "GUICONTROLS");
/*  82 */     guiClassNameMap.put(bwd.class, "GUICREATEFLATWORLD");
/*  83 */     guiClassNameMap.put(bwf.class, "GUICREATEWORLD");
/*  84 */     guiClassNameMap.put(bxj.class, "GUICUSTOMIZESKIN");
/*  85 */     guiClassNameMap.put(bwj.class, "GUICUSTOMIZEWORLDSCREEN");
/*  86 */     guiClassNameMap.put(bwo.class, "GUIDISCONNECTED");
/*  87 */     guiClassNameMap.put(bxd.class, "GUIDOWNLOADTERRAIN");
/*  88 */     guiClassNameMap.put(bzf.class, "GUIENCHANTMENT");
/*  89 */     guiClassNameMap.put(bwq.class, "GUIERRORSCREEN");
/*  90 */     guiClassNameMap.put(bwz.class, "GUIFLATPRESETS");
/*  91 */     guiClassNameMap.put(bwl.class, "GUIGAMEOVER");
/*  92 */     guiClassNameMap.put(bzh.class, "GUIHOPPER");
/*  93 */     guiClassNameMap.put(bwy.class, "GUIINGAMEMENU");
/*  94 */     guiClassNameMap.put(bws.class, "GUILANGUAGE");
/*  95 */     guiClassNameMap.put(bxq.class, "GUIMAINMENU");
/*  96 */     guiClassNameMap.put(bwx.class, "GUIMEMORYERRORSCREEN");
/*  97 */     guiClassNameMap.put(bzk.class, "GUIMERCHANT");
/*  98 */     guiClassNameMap.put(bzp.class, "GUIMULTIPLAYER");
/*  99 */     guiClassNameMap.put(bwv.class, "GUIOPTIONS");
/* 100 */     guiClassNameMap.put(bxe.class, "GUIRENAMEWORLD");
/* 101 */     guiClassNameMap.put(bym.class, "GUIREPAIR");
/* 102 */     guiClassNameMap.put(bwp.class, "GUISCREENADDSERVER");
/* 103 */     guiClassNameMap.put(bys.class, "GUISCREENBOOK");
/* 104 */     guiClassNameMap.put(bwg.class, "GUISCREENCUSTOMIZEPRESETS");
/* 105 */     guiClassNameMap.put(bwm.class, "GUISCREENDEMO");
/* 106 */     guiClassNameMap.put(bxo.class, "GUISCREENOPTIONSSOUNDS");
/* 107 */     guiClassNameMap.put(bvt.class, "GUISCREENREALMSPROXY");
/* 108 */     guiClassNameMap.put(bzx.class, "GUISCREENRESOURCEPACKS");
/* 109 */     guiClassNameMap.put(bwn.class, "GUISCREENSERVERLIST");
/* 110 */     guiClassNameMap.put(bxc.class, "GUISCREENWORKING");
/* 111 */     guiClassNameMap.put(bxg.class, "GUISELECTWORLD");
/* 112 */     guiClassNameMap.put(bxi.class, "GUISHARETOLAN");
/* 113 */     guiClassNameMap.put(bwr.class, "GUISLEEPMP");
/* 114 */     guiClassNameMap.put(bxm.class, "GUISNOOPER");
/* 115 */     guiClassNameMap.put(bxr.class, "GUIVIDEOSETTINGS");
/* 116 */     guiClassNameMap.put(bxs.class, "GUIWINGAME");
/* 117 */     guiClassNameMap.put(bwa.class, "GUIYESNO");
/* 118 */     guiClassNameMap.put(byn.class, "GUIBEACON");
/* 119 */     guiClassNameMap.put(byu.class, "GUIBREWINGSTAND");
/* 120 */     guiClassNameMap.put(byw.class, "GUICHEST");
/* 121 */     guiClassNameMap.put(byz.class, "GUICONTAINERCREATIVE");
/* 122 */     guiClassNameMap.put(byx.class, "GUICRAFTING");
/* 123 */     guiClassNameMap.put(bzc.class, "GUIDISPENSER");
/* 124 */     guiClassNameMap.put(bzm.class, "GUIEDITSIGN");
/* 125 */     guiClassNameMap.put(bzg.class, "GUIFURNACE");
/* 126 */     guiClassNameMap.put(bzj.class, "GUIINVENTORY");
/* 127 */     guiClassNameMap.put(bzi.class, "GUISCREENHORSEINVENTORY");
/* 128 */     guiClassNameMap.put(bvw.class, "SCREENCHATOPTIONS");
/* 129 */     guiClassNameMap.put(cah.class, "GUIINGESTSERVERS");
/* 130 */     guiClassNameMap.put(caj.class, "GUISTREAMOPTIONS");
/* 131 */     guiClassNameMap.put(cak.class, "GUISTREAMUNAVAILABLE");
/* 132 */     guiClassNameMap.put(can.class, "GUITWITCHUSERMODE");
/* 133 */     guiClassNameMap.put(bwb.class, "GUICONNECTING");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   private String facingDirection = "S";
/*     */   
/*     */   private bdj sign;
/*     */   
/* 148 */   private String[] signLines = new String[] { "", "", "", "" };
/*     */   
/*     */   private int signUpdateTicks;
/*     */   
/*     */   private BlockPropertyTracker hitTracker;
/*     */ 
/*     */   
/*     */   public VariableProviderPlayer() {
/* 156 */     this.hitTracker = new BlockPropertyTracker("HIT_", (IVariableStore)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {
/*     */     // Byte code:
/*     */     //   0: invokestatic getPlayer : ()Lcio;
/*     */     //   3: astore_2
/*     */     //   4: invokestatic z : ()Lbsu;
/*     */     //   7: astore_3
/*     */     //   8: aload_3
/*     */     //   9: getfield y : Luw;
/*     */     //   12: astore #4
/*     */     //   14: aload #4
/*     */     //   16: ldc 'screen'
/*     */     //   18: invokevirtual a : (Ljava/lang/String;)V
/*     */     //   21: aload_0
/*     */     //   22: ldc 'GUI'
/*     */     //   24: invokestatic getNameOfCurrentGuiScreen : ()Ljava/lang/String;
/*     */     //   27: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   30: aload_0
/*     */     //   31: ldc 'DISPLAYWIDTH'
/*     */     //   33: aload_3
/*     */     //   34: getfield d : I
/*     */     //   37: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   40: aload_0
/*     */     //   41: ldc 'DISPLAYHEIGHT'
/*     */     //   43: aload_3
/*     */     //   44: getfield e : I
/*     */     //   47: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   50: aload_3
/*     */     //   51: getfield m : Lbxf;
/*     */     //   54: ifnull -> 89
/*     */     //   57: aload_3
/*     */     //   58: getfield m : Lbxf;
/*     */     //   61: instanceof net/eq2online/macros/gui/screens/GuiCustomGui
/*     */     //   64: ifeq -> 89
/*     */     //   67: aload_0
/*     */     //   68: ldc 'SCREEN'
/*     */     //   70: aload_3
/*     */     //   71: getfield m : Lbxf;
/*     */     //   74: checkcast net/eq2online/macros/gui/screens/GuiCustomGui
/*     */     //   77: invokevirtual getLayout : ()Lnet/eq2online/macros/gui/designable/DesignableGuiLayout;
/*     */     //   80: getfield name : Ljava/lang/String;
/*     */     //   83: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   86: goto -> 97
/*     */     //   89: aload_0
/*     */     //   90: ldc 'SCREEN'
/*     */     //   92: ldc ''
/*     */     //   94: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   97: aload_2
/*     */     //   98: ifnull -> 955
/*     */     //   101: aload #4
/*     */     //   103: ldc 'position'
/*     */     //   105: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   108: aload_2
/*     */     //   109: getfield s : D
/*     */     //   112: invokestatic c : (D)I
/*     */     //   115: istore #5
/*     */     //   117: aload_2
/*     */     //   118: getfield t : D
/*     */     //   121: invokestatic c : (D)I
/*     */     //   124: istore #6
/*     */     //   126: aload_2
/*     */     //   127: getfield u : D
/*     */     //   130: invokestatic c : (D)I
/*     */     //   133: istore #7
/*     */     //   135: new dt
/*     */     //   138: dup
/*     */     //   139: iload #5
/*     */     //   141: iload #6
/*     */     //   143: iload #7
/*     */     //   145: invokespecial <init> : (III)V
/*     */     //   148: astore #8
/*     */     //   150: aload_2
/*     */     //   151: getfield y : F
/*     */     //   154: ldc 360.0
/*     */     //   156: frem
/*     */     //   157: f2i
/*     */     //   158: istore #9
/*     */     //   160: iload #9
/*     */     //   162: sipush #180
/*     */     //   165: isub
/*     */     //   166: istore #10
/*     */     //   168: aload_2
/*     */     //   169: getfield z : F
/*     */     //   172: ldc 360.0
/*     */     //   174: frem
/*     */     //   175: f2i
/*     */     //   176: istore #11
/*     */     //   178: iload #9
/*     */     //   180: ifge -> 192
/*     */     //   183: wide iinc #9 360
/*     */     //   189: goto -> 178
/*     */     //   192: iload #10
/*     */     //   194: ifge -> 206
/*     */     //   197: wide iinc #10 360
/*     */     //   203: goto -> 192
/*     */     //   206: iload #11
/*     */     //   208: ifge -> 220
/*     */     //   211: wide iinc #11 360
/*     */     //   217: goto -> 206
/*     */     //   220: aload_0
/*     */     //   221: getfield prevRotationYaw : F
/*     */     //   224: aload_2
/*     */     //   225: getfield y : F
/*     */     //   228: fcmpl
/*     */     //   229: ifeq -> 311
/*     */     //   232: aload_0
/*     */     //   233: aload_2
/*     */     //   234: getfield y : F
/*     */     //   237: putfield prevRotationYaw : F
/*     */     //   240: aload_0
/*     */     //   241: ldc 'S'
/*     */     //   243: putfield facingDirection : Ljava/lang/String;
/*     */     //   246: iload #9
/*     */     //   248: bipush #45
/*     */     //   250: if_icmplt -> 267
/*     */     //   253: iload #9
/*     */     //   255: sipush #135
/*     */     //   258: if_icmpge -> 267
/*     */     //   261: aload_0
/*     */     //   262: ldc 'W'
/*     */     //   264: putfield facingDirection : Ljava/lang/String;
/*     */     //   267: iload #9
/*     */     //   269: sipush #135
/*     */     //   272: if_icmplt -> 289
/*     */     //   275: iload #9
/*     */     //   277: sipush #225
/*     */     //   280: if_icmpge -> 289
/*     */     //   283: aload_0
/*     */     //   284: ldc 'N'
/*     */     //   286: putfield facingDirection : Ljava/lang/String;
/*     */     //   289: iload #9
/*     */     //   291: sipush #225
/*     */     //   294: if_icmplt -> 311
/*     */     //   297: iload #9
/*     */     //   299: sipush #315
/*     */     //   302: if_icmpge -> 311
/*     */     //   305: aload_0
/*     */     //   306: ldc 'E'
/*     */     //   308: putfield facingDirection : Ljava/lang/String;
/*     */     //   311: aload #4
/*     */     //   313: ldc 'biome'
/*     */     //   315: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   318: ldc 'UNKNOWN'
/*     */     //   320: astore #12
/*     */     //   322: aload_3
/*     */     //   323: getfield f : Lcen;
/*     */     //   326: ifnull -> 371
/*     */     //   329: aload_3
/*     */     //   330: getfield f : Lcen;
/*     */     //   333: aload #8
/*     */     //   335: invokevirtual e : (Ldt;)Z
/*     */     //   338: ifeq -> 371
/*     */     //   341: aload_3
/*     */     //   342: getfield f : Lcen;
/*     */     //   345: aload #8
/*     */     //   347: invokevirtual f : (Ldt;)Lbfh;
/*     */     //   350: astore #13
/*     */     //   352: aload #13
/*     */     //   354: aload #8
/*     */     //   356: aload_3
/*     */     //   357: getfield f : Lcen;
/*     */     //   360: invokevirtual v : ()Larz;
/*     */     //   363: invokevirtual a : (Ldt;Larz;)Larm;
/*     */     //   366: getfield ah : Ljava/lang/String;
/*     */     //   369: astore #12
/*     */     //   371: aload #4
/*     */     //   373: ldc 'mode'
/*     */     //   375: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   378: iconst_0
/*     */     //   379: istore #13
/*     */     //   381: ldc 'NOT_SET'
/*     */     //   383: astore #14
/*     */     //   385: aload_3
/*     */     //   386: invokevirtual t : ()Lcee;
/*     */     //   389: aload_2
/*     */     //   390: invokevirtual aJ : ()Ljava/util/UUID;
/*     */     //   393: invokevirtual a : (Ljava/util/UUID;)Lces;
/*     */     //   396: astore #15
/*     */     //   398: aload #15
/*     */     //   400: ifnull -> 424
/*     */     //   403: aload #15
/*     */     //   405: invokevirtual b : ()Larc;
/*     */     //   408: astore #16
/*     */     //   410: aload #16
/*     */     //   412: invokevirtual a : ()I
/*     */     //   415: istore #13
/*     */     //   417: aload #16
/*     */     //   419: invokevirtual name : ()Ljava/lang/String;
/*     */     //   422: astore #14
/*     */     //   424: ldc 'NONE'
/*     */     //   426: astore #16
/*     */     //   428: fconst_0
/*     */     //   429: fstore #17
/*     */     //   431: aload_2
/*     */     //   432: getfield m : Lwv;
/*     */     //   435: ifnull -> 525
/*     */     //   438: aload_2
/*     */     //   439: getfield m : Lwv;
/*     */     //   442: invokevirtual d_ : ()Ljava/lang/String;
/*     */     //   445: astore #16
/*     */     //   447: aload_2
/*     */     //   448: getfield m : Lwv;
/*     */     //   451: instanceof xn
/*     */     //   454: ifeq -> 470
/*     */     //   457: aload_2
/*     */     //   458: getfield m : Lwv;
/*     */     //   461: checkcast xn
/*     */     //   464: invokevirtual bm : ()F
/*     */     //   467: goto -> 523
/*     */     //   470: aload_2
/*     */     //   471: getfield m : Lwv;
/*     */     //   474: instanceof adx
/*     */     //   477: ifeq -> 496
/*     */     //   480: ldc 40.0
/*     */     //   482: aload_2
/*     */     //   483: getfield m : Lwv;
/*     */     //   486: checkcast adx
/*     */     //   489: invokevirtual p : ()F
/*     */     //   492: fsub
/*     */     //   493: goto -> 523
/*     */     //   496: aload_2
/*     */     //   497: getfield m : Lwv;
/*     */     //   500: instanceof adu
/*     */     //   503: ifeq -> 522
/*     */     //   506: ldc 40.0
/*     */     //   508: aload_2
/*     */     //   509: getfield m : Lwv;
/*     */     //   512: checkcast adu
/*     */     //   515: invokevirtual j : ()F
/*     */     //   518: fsub
/*     */     //   519: goto -> 523
/*     */     //   522: fconst_0
/*     */     //   523: fstore #17
/*     */     //   525: aload_0
/*     */     //   526: ldc_w 'MODE'
/*     */     //   529: iload #13
/*     */     //   531: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   534: aload_0
/*     */     //   535: ldc_w 'GAMEMODE'
/*     */     //   538: aload #14
/*     */     //   540: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   543: aload #4
/*     */     //   545: ldc_w 'name'
/*     */     //   548: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   551: aload_0
/*     */     //   552: ldc_w 'PLAYER'
/*     */     //   555: aload_2
/*     */     //   556: invokevirtual d_ : ()Ljava/lang/String;
/*     */     //   559: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   562: aload_0
/*     */     //   563: ldc_w 'DISPLAYNAME'
/*     */     //   566: aload_2
/*     */     //   567: invokevirtual e_ : ()Lho;
/*     */     //   570: invokeinterface d : ()Ljava/lang/String;
/*     */     //   575: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   578: aload_0
/*     */     //   579: ldc_w 'UUID'
/*     */     //   582: aload_2
/*     */     //   583: invokevirtual aJ : ()Ljava/util/UUID;
/*     */     //   586: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   589: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   592: aload #4
/*     */     //   594: ldc_w 'health'
/*     */     //   597: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   600: aload_0
/*     */     //   601: ldc_w 'HEALTH'
/*     */     //   604: aload_2
/*     */     //   605: invokevirtual bm : ()F
/*     */     //   608: invokestatic d : (F)I
/*     */     //   611: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   614: aload_0
/*     */     //   615: ldc_w 'ARMOUR'
/*     */     //   618: aload_2
/*     */     //   619: invokevirtual bq : ()I
/*     */     //   622: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   625: aload_0
/*     */     //   626: ldc_w 'HUNGER'
/*     */     //   629: aload_2
/*     */     //   630: invokevirtual ck : ()Lahz;
/*     */     //   633: invokevirtual a : ()I
/*     */     //   636: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   639: aload_0
/*     */     //   640: ldc_w 'SATURATION'
/*     */     //   643: aload_2
/*     */     //   644: invokevirtual ck : ()Lahz;
/*     */     //   647: invokevirtual e : ()F
/*     */     //   650: invokestatic f : (F)I
/*     */     //   653: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   656: aload_0
/*     */     //   657: ldc_w 'INVSLOT'
/*     */     //   660: aload_2
/*     */     //   661: getfield bg : Lahb;
/*     */     //   664: getfield c : I
/*     */     //   667: iconst_1
/*     */     //   668: iadd
/*     */     //   669: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   672: aload_0
/*     */     //   673: ldc_w 'OXYGEN'
/*     */     //   676: aload_2
/*     */     //   677: invokevirtual aA : ()I
/*     */     //   680: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   683: aload #4
/*     */     //   685: ldc_w 'xp'
/*     */     //   688: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   691: aload_0
/*     */     //   692: ldc_w 'XP'
/*     */     //   695: aload_2
/*     */     //   696: getfield bB : F
/*     */     //   699: aload_2
/*     */     //   700: invokevirtual cj : ()I
/*     */     //   703: i2f
/*     */     //   704: fmul
/*     */     //   705: f2i
/*     */     //   706: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   709: aload_0
/*     */     //   710: ldc_w 'TOTALXP'
/*     */     //   713: aload_2
/*     */     //   714: getfield bA : I
/*     */     //   717: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   720: aload_0
/*     */     //   721: ldc_w 'LEVEL'
/*     */     //   724: aload_2
/*     */     //   725: getfield bz : I
/*     */     //   728: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   731: aload #4
/*     */     //   733: ldc 'position'
/*     */     //   735: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   738: aload_0
/*     */     //   739: ldc_w 'XPOS'
/*     */     //   742: iload #5
/*     */     //   744: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   747: aload_0
/*     */     //   748: ldc_w 'YPOS'
/*     */     //   751: iload #6
/*     */     //   753: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   756: aload_0
/*     */     //   757: ldc_w 'ZPOS'
/*     */     //   760: iload #7
/*     */     //   762: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   765: aload_0
/*     */     //   766: ldc_w 'YAW'
/*     */     //   769: iload #9
/*     */     //   771: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   774: aload_0
/*     */     //   775: ldc_w 'CARDINALYAW'
/*     */     //   778: iload #10
/*     */     //   780: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   783: aload_0
/*     */     //   784: ldc_w 'PITCH'
/*     */     //   787: iload #11
/*     */     //   789: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   792: aload_0
/*     */     //   793: ldc_w 'DIRECTION'
/*     */     //   796: aload_0
/*     */     //   797: getfield facingDirection : Ljava/lang/String;
/*     */     //   800: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   803: aload #4
/*     */     //   805: ldc_w 'env'
/*     */     //   808: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   811: aload_0
/*     */     //   812: ldc_w 'LIGHT'
/*     */     //   815: aload_3
/*     */     //   816: getfield f : Lcen;
/*     */     //   819: aload #8
/*     */     //   821: invokevirtual k : (Ldt;)I
/*     */     //   824: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   827: aload_0
/*     */     //   828: ldc_w 'DIMENSION'
/*     */     //   831: aload_2
/*     */     //   832: getfield am : I
/*     */     //   835: aload_2
/*     */     //   836: getfield o : Laqu;
/*     */     //   839: invokestatic getNameForDimension : (ILaqu;)Ljava/lang/String;
/*     */     //   842: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   845: aload_0
/*     */     //   846: ldc_w 'BIOME'
/*     */     //   849: aload #12
/*     */     //   851: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   854: aload_0
/*     */     //   855: ldc_w 'VEHICLE'
/*     */     //   858: aload #16
/*     */     //   860: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   863: aload_0
/*     */     //   864: ldc_w 'VEHICLEHEALTH'
/*     */     //   867: fload #17
/*     */     //   869: invokestatic d : (F)I
/*     */     //   872: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   875: aload_0
/*     */     //   876: ldc_w 'FLYING'
/*     */     //   879: aload_2
/*     */     //   880: getfield by : Laha;
/*     */     //   883: getfield b : Z
/*     */     //   886: invokevirtual storeVariable : (Ljava/lang/String;Z)V
/*     */     //   889: aload_0
/*     */     //   890: ldc_w 'CANFLY'
/*     */     //   893: aload_2
/*     */     //   894: getfield by : Laha;
/*     */     //   897: getfield c : Z
/*     */     //   900: invokevirtual storeVariable : (Ljava/lang/String;Z)V
/*     */     //   903: aload_0
/*     */     //   904: aload_3
/*     */     //   905: aload_2
/*     */     //   906: aload #8
/*     */     //   908: invokespecial getDifficulty : (Lbsu;Lcio;Ldt;)Lvu;
/*     */     //   911: astore #18
/*     */     //   913: aload #18
/*     */     //   915: invokevirtual b : ()F
/*     */     //   918: ldc_w 100.0
/*     */     //   921: fmul
/*     */     //   922: f2i
/*     */     //   923: istore #19
/*     */     //   925: aload_0
/*     */     //   926: ldc_w 'LOCALDIFFICULTY'
/*     */     //   929: iload #19
/*     */     //   931: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   934: aload #4
/*     */     //   936: ldc_w 'container'
/*     */     //   939: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   942: aload_0
/*     */     //   943: ldc_w 'CONTAINERSLOTS'
/*     */     //   946: invokestatic getContainerSize : ()I
/*     */     //   949: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   952: goto -> 1220
/*     */     //   955: aload #4
/*     */     //   957: ldc_w 'stats'
/*     */     //   960: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   963: aload_0
/*     */     //   964: ldc_w 'PLAYER'
/*     */     //   967: ldc_w 'Player'
/*     */     //   970: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   973: aload_0
/*     */     //   974: ldc_w 'DISPLAYNAME'
/*     */     //   977: ldc_w 'Player'
/*     */     //   980: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   983: aload_0
/*     */     //   984: ldc_w 'UUID'
/*     */     //   987: ldc ''
/*     */     //   989: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   992: aload_0
/*     */     //   993: ldc_w 'HEALTH'
/*     */     //   996: bipush #20
/*     */     //   998: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1001: aload_0
/*     */     //   1002: ldc_w 'ARMOUR'
/*     */     //   1005: bipush #20
/*     */     //   1007: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1010: aload_0
/*     */     //   1011: ldc_w 'HUNGER'
/*     */     //   1014: bipush #20
/*     */     //   1016: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1019: aload_0
/*     */     //   1020: ldc_w 'SATURATION'
/*     */     //   1023: iconst_0
/*     */     //   1024: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1027: aload_0
/*     */     //   1028: ldc_w 'INVSLOT'
/*     */     //   1031: iconst_1
/*     */     //   1032: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1035: aload_0
/*     */     //   1036: ldc_w 'XP'
/*     */     //   1039: iconst_0
/*     */     //   1040: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1043: aload_0
/*     */     //   1044: ldc_w 'TOTALXP'
/*     */     //   1047: iconst_0
/*     */     //   1048: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1051: aload_0
/*     */     //   1052: ldc_w 'LEVEL'
/*     */     //   1055: iconst_0
/*     */     //   1056: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1059: aload_0
/*     */     //   1060: ldc_w 'MODE'
/*     */     //   1063: iconst_0
/*     */     //   1064: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1067: aload_0
/*     */     //   1068: ldc_w 'GAMEMODE'
/*     */     //   1071: ldc 'NOT_SET'
/*     */     //   1073: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1076: aload_0
/*     */     //   1077: ldc_w 'LIGHT'
/*     */     //   1080: bipush #15
/*     */     //   1082: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1085: aload_0
/*     */     //   1086: ldc_w 'XPOS'
/*     */     //   1089: iconst_0
/*     */     //   1090: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1093: aload_0
/*     */     //   1094: ldc_w 'YPOS'
/*     */     //   1097: iconst_0
/*     */     //   1098: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1101: aload_0
/*     */     //   1102: ldc_w 'ZPOS'
/*     */     //   1105: iconst_0
/*     */     //   1106: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1109: aload_0
/*     */     //   1110: ldc_w 'YAW'
/*     */     //   1113: iconst_0
/*     */     //   1114: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1117: aload_0
/*     */     //   1118: ldc_w 'CARDINALYAW'
/*     */     //   1121: sipush #180
/*     */     //   1124: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1127: aload_0
/*     */     //   1128: ldc_w 'PITCH'
/*     */     //   1131: iconst_0
/*     */     //   1132: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1135: aload_0
/*     */     //   1136: ldc_w 'DIRECTION'
/*     */     //   1139: ldc 'S'
/*     */     //   1141: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1144: aload_0
/*     */     //   1145: ldc_w 'DIMENSION'
/*     */     //   1148: ldc_w 'SURFACE'
/*     */     //   1151: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1154: aload_0
/*     */     //   1155: ldc_w 'OXYGEN'
/*     */     //   1158: iconst_0
/*     */     //   1159: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1162: aload_0
/*     */     //   1163: ldc_w 'BIOME'
/*     */     //   1166: ldc 'UNKNOWN'
/*     */     //   1168: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1171: aload_0
/*     */     //   1172: ldc_w 'VEHICLE'
/*     */     //   1175: ldc 'NONE'
/*     */     //   1177: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1180: aload_0
/*     */     //   1181: ldc_w 'VEHICLEHEALTH'
/*     */     //   1184: iconst_0
/*     */     //   1185: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1188: aload_0
/*     */     //   1189: ldc_w 'FLYING'
/*     */     //   1192: iconst_0
/*     */     //   1193: invokevirtual storeVariable : (Ljava/lang/String;Z)V
/*     */     //   1196: aload_0
/*     */     //   1197: ldc_w 'CANFLY'
/*     */     //   1200: iconst_0
/*     */     //   1201: invokevirtual storeVariable : (Ljava/lang/String;Z)V
/*     */     //   1204: aload_0
/*     */     //   1205: ldc_w 'CONTAINERSLOTS'
/*     */     //   1208: iconst_0
/*     */     //   1209: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1212: aload_0
/*     */     //   1213: ldc_w 'LOCALDIFFICULTY'
/*     */     //   1216: iconst_0
/*     */     //   1217: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1220: aload #4
/*     */     //   1222: ldc_w 'armour'
/*     */     //   1225: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   1228: aload_0
/*     */     //   1229: aload_2
/*     */     //   1230: iconst_0
/*     */     //   1231: ldc_w 'BOOTS'
/*     */     //   1234: invokevirtual updateArmourSlotVariables : (Lcio;ILjava/lang/String;)V
/*     */     //   1237: aload_0
/*     */     //   1238: aload_2
/*     */     //   1239: iconst_1
/*     */     //   1240: ldc_w 'LEGGINGS'
/*     */     //   1243: invokevirtual updateArmourSlotVariables : (Lcio;ILjava/lang/String;)V
/*     */     //   1246: aload_0
/*     */     //   1247: aload_2
/*     */     //   1248: iconst_2
/*     */     //   1249: ldc_w 'CHESTPLATE'
/*     */     //   1252: invokevirtual updateArmourSlotVariables : (Lcio;ILjava/lang/String;)V
/*     */     //   1255: aload_0
/*     */     //   1256: aload_2
/*     */     //   1257: iconst_3
/*     */     //   1258: ldc_w 'HELM'
/*     */     //   1261: invokevirtual updateArmourSlotVariables : (Lcio;ILjava/lang/String;)V
/*     */     //   1264: aload #4
/*     */     //   1266: ldc_w 'inventory'
/*     */     //   1269: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   1272: aload_2
/*     */     //   1273: ifnull -> 1471
/*     */     //   1276: aload_2
/*     */     //   1277: getfield bg : Lahb;
/*     */     //   1280: ifnull -> 1287
/*     */     //   1283: iconst_1
/*     */     //   1284: goto -> 1288
/*     */     //   1287: iconst_0
/*     */     //   1288: aload_2
/*     */     //   1289: getfield bg : Lahb;
/*     */     //   1292: invokevirtual h : ()Lamj;
/*     */     //   1295: ifnull -> 1302
/*     */     //   1298: iconst_1
/*     */     //   1299: goto -> 1303
/*     */     //   1302: iconst_0
/*     */     //   1303: iand
/*     */     //   1304: ifeq -> 1471
/*     */     //   1307: aload_2
/*     */     //   1308: getfield bg : Lahb;
/*     */     //   1311: invokevirtual h : ()Lamj;
/*     */     //   1314: astore #5
/*     */     //   1316: aload #5
/*     */     //   1318: invokevirtual b : ()Lalq;
/*     */     //   1321: aload #5
/*     */     //   1323: invokevirtual e_ : (Lamj;)Ljava/lang/String;
/*     */     //   1326: astore #6
/*     */     //   1328: aload #5
/*     */     //   1330: invokevirtual b : ()Lalq;
/*     */     //   1333: invokestatic getItemName : (Lalq;)Ljava/lang/String;
/*     */     //   1336: astore #7
/*     */     //   1338: ldc_w '%s:%d'
/*     */     //   1341: iconst_2
/*     */     //   1342: anewarray java/lang/Object
/*     */     //   1345: dup
/*     */     //   1346: iconst_0
/*     */     //   1347: aload #7
/*     */     //   1349: aastore
/*     */     //   1350: dup
/*     */     //   1351: iconst_1
/*     */     //   1352: aload #5
/*     */     //   1354: invokevirtual f : ()Z
/*     */     //   1357: ifeq -> 1368
/*     */     //   1360: aload #5
/*     */     //   1362: invokevirtual i : ()I
/*     */     //   1365: goto -> 1369
/*     */     //   1368: iconst_0
/*     */     //   1369: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   1372: aastore
/*     */     //   1373: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   1376: astore #8
/*     */     //   1378: aload #6
/*     */     //   1380: ifnonnull -> 1387
/*     */     //   1383: ldc ''
/*     */     //   1385: astore #6
/*     */     //   1387: aload_0
/*     */     //   1388: ldc_w 'ITEM'
/*     */     //   1391: aload #7
/*     */     //   1393: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1396: aload_0
/*     */     //   1397: ldc_w 'ITEMIDDMG'
/*     */     //   1400: aload #8
/*     */     //   1402: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1405: aload_0
/*     */     //   1406: ldc_w 'ITEMCODE'
/*     */     //   1409: aload #6
/*     */     //   1411: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1414: aload_0
/*     */     //   1415: ldc_w 'ITEMNAME'
/*     */     //   1418: aload #5
/*     */     //   1420: invokevirtual q : ()Ljava/lang/String;
/*     */     //   1423: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1426: aload_0
/*     */     //   1427: ldc_w 'DURABILITY'
/*     */     //   1430: aload #5
/*     */     //   1432: invokevirtual j : ()I
/*     */     //   1435: aload #5
/*     */     //   1437: invokevirtual i : ()I
/*     */     //   1440: isub
/*     */     //   1441: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1444: aload_0
/*     */     //   1445: ldc_w 'ITEMDAMAGE'
/*     */     //   1448: aload #5
/*     */     //   1450: invokevirtual j : ()I
/*     */     //   1453: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1456: aload_0
/*     */     //   1457: ldc_w 'STACKSIZE'
/*     */     //   1460: aload #5
/*     */     //   1462: getfield b : I
/*     */     //   1465: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1468: goto -> 1536
/*     */     //   1471: aload_0
/*     */     //   1472: ldc_w 'ITEM'
/*     */     //   1475: aconst_null
/*     */     //   1476: invokestatic getItemName : (Lalq;)Ljava/lang/String;
/*     */     //   1479: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1482: aload_0
/*     */     //   1483: ldc_w 'ITEMIDDMG'
/*     */     //   1486: ldc_w 'air:0'
/*     */     //   1489: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1492: aload_0
/*     */     //   1493: ldc_w 'ITEMCODE'
/*     */     //   1496: ldc_w 'air'
/*     */     //   1499: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1502: aload_0
/*     */     //   1503: ldc_w 'ITEMNAME'
/*     */     //   1506: ldc_w 'Hand'
/*     */     //   1509: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1512: aload_0
/*     */     //   1513: ldc_w 'DURABILITY'
/*     */     //   1516: iconst_0
/*     */     //   1517: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1520: aload_0
/*     */     //   1521: ldc_w 'ITEMDAMAGE'
/*     */     //   1524: iconst_0
/*     */     //   1525: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1528: aload_0
/*     */     //   1529: ldc_w 'STACKSIZE'
/*     */     //   1532: iconst_0
/*     */     //   1533: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1536: aload_3
/*     */     //   1537: getfield s : Lbru;
/*     */     //   1540: astore #5
/*     */     //   1542: iconst_0
/*     */     //   1543: istore #6
/*     */     //   1545: iconst_1
/*     */     //   1546: istore #7
/*     */     //   1548: aload #4
/*     */     //   1550: ldc_w 'hit'
/*     */     //   1553: invokevirtual c : (Ljava/lang/String;)V
/*     */     //   1556: aload_2
/*     */     //   1557: ifnull -> 1963
/*     */     //   1560: aload #5
/*     */     //   1562: ifnull -> 1963
/*     */     //   1565: aload #5
/*     */     //   1567: getfield a : Lbrv;
/*     */     //   1570: getstatic brv.b : Lbrv;
/*     */     //   1573: if_acmpne -> 1963
/*     */     //   1576: aload #5
/*     */     //   1578: invokevirtual a : ()Ldt;
/*     */     //   1581: astore #8
/*     */     //   1583: aload_3
/*     */     //   1584: getfield f : Lcen;
/*     */     //   1587: aload #8
/*     */     //   1589: invokevirtual p : (Ldt;)Lbec;
/*     */     //   1592: astore #9
/*     */     //   1594: aload #9
/*     */     //   1596: invokeinterface c : ()Latr;
/*     */     //   1601: astore #10
/*     */     //   1603: aload #10
/*     */     //   1605: aload #9
/*     */     //   1607: aload_3
/*     */     //   1608: getfield f : Lcen;
/*     */     //   1611: aload #8
/*     */     //   1613: invokevirtual a : (Lbec;Lard;Ldt;)Lbec;
/*     */     //   1616: astore #11
/*     */     //   1618: aload #10
/*     */     //   1620: aload_3
/*     */     //   1621: getfield f : Lcen;
/*     */     //   1624: aload #8
/*     */     //   1626: invokevirtual b : (Laqu;Ldt;)Lalq;
/*     */     //   1629: astore #12
/*     */     //   1631: aload #10
/*     */     //   1633: aload #9
/*     */     //   1635: invokevirtual a : (Lbec;)I
/*     */     //   1638: istore #13
/*     */     //   1640: aload #10
/*     */     //   1642: invokevirtual H : ()Ljava/lang/String;
/*     */     //   1645: astore #14
/*     */     //   1647: new amj
/*     */     //   1650: dup
/*     */     //   1651: aload #12
/*     */     //   1653: iconst_1
/*     */     //   1654: iload #13
/*     */     //   1656: invokespecial <init> : (Lalq;II)V
/*     */     //   1659: astore #15
/*     */     //   1661: aload #15
/*     */     //   1663: invokevirtual q : ()Ljava/lang/String;
/*     */     //   1666: astore #14
/*     */     //   1668: goto -> 1673
/*     */     //   1671: astore #15
/*     */     //   1673: aload_0
/*     */     //   1674: ldc_w 'HIT'
/*     */     //   1677: ldc_w 'TILE'
/*     */     //   1680: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1683: aload_0
/*     */     //   1684: ldc_w 'HITNAME'
/*     */     //   1687: aload #14
/*     */     //   1689: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1692: aload_0
/*     */     //   1693: ldc_w 'HITID'
/*     */     //   1696: aload #10
/*     */     //   1698: invokestatic getBlockName : (Latr;)Ljava/lang/String;
/*     */     //   1701: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1704: aload_0
/*     */     //   1705: ldc_w 'HITDATA'
/*     */     //   1708: iload #13
/*     */     //   1710: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1713: aload_0
/*     */     //   1714: ldc_w 'HITUUID'
/*     */     //   1717: ldc ''
/*     */     //   1719: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1722: aload_0
/*     */     //   1723: ldc_w 'HITX'
/*     */     //   1726: aload #8
/*     */     //   1728: invokevirtual n : ()I
/*     */     //   1731: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1734: aload_0
/*     */     //   1735: ldc_w 'HITY'
/*     */     //   1738: aload #8
/*     */     //   1740: invokevirtual o : ()I
/*     */     //   1743: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1746: aload_0
/*     */     //   1747: ldc_w 'HITZ'
/*     */     //   1750: aload #8
/*     */     //   1752: invokevirtual p : ()I
/*     */     //   1755: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   1758: aload_0
/*     */     //   1759: ldc_w 'HITSIDE'
/*     */     //   1762: aload_0
/*     */     //   1763: aload #5
/*     */     //   1765: getfield b : Lej;
/*     */     //   1768: invokevirtual a : ()I
/*     */     //   1771: invokespecial getSideName : (I)Ljava/lang/String;
/*     */     //   1774: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   1777: aload_0
/*     */     //   1778: getfield hitTracker : Lnet/eq2online/macros/scripting/variable/BlockPropertyTracker;
/*     */     //   1781: aload #11
/*     */     //   1783: invokevirtual update : (Lbec;)V
/*     */     //   1786: aload #10
/*     */     //   1788: instanceof bai
/*     */     //   1791: ifeq -> 1879
/*     */     //   1794: aload_3
/*     */     //   1795: getfield f : Lcen;
/*     */     //   1798: aload #8
/*     */     //   1800: invokevirtual s : (Ldt;)Lbcm;
/*     */     //   1803: astore #15
/*     */     //   1805: aload #15
/*     */     //   1807: instanceof bdj
/*     */     //   1810: ifeq -> 1879
/*     */     //   1813: aload #15
/*     */     //   1815: aload_0
/*     */     //   1816: getfield sign : Lbdj;
/*     */     //   1819: if_acmpne -> 1831
/*     */     //   1822: aload_0
/*     */     //   1823: getfield signUpdateTicks : I
/*     */     //   1826: bipush #50
/*     */     //   1828: if_icmple -> 1865
/*     */     //   1831: aload_0
/*     */     //   1832: iconst_0
/*     */     //   1833: putfield signUpdateTicks : I
/*     */     //   1836: aload_0
/*     */     //   1837: aload #15
/*     */     //   1839: checkcast bdj
/*     */     //   1842: putfield sign : Lbdj;
/*     */     //   1845: aload_0
/*     */     //   1846: iconst_0
/*     */     //   1847: invokespecial readSignLine : (I)V
/*     */     //   1850: aload_0
/*     */     //   1851: iconst_1
/*     */     //   1852: invokespecial readSignLine : (I)V
/*     */     //   1855: aload_0
/*     */     //   1856: iconst_2
/*     */     //   1857: invokespecial readSignLine : (I)V
/*     */     //   1860: aload_0
/*     */     //   1861: iconst_3
/*     */     //   1862: invokespecial readSignLine : (I)V
/*     */     //   1865: iconst_0
/*     */     //   1866: istore #7
/*     */     //   1868: aload_0
/*     */     //   1869: ldc_w 'SIGNTEXT'
/*     */     //   1872: aload_0
/*     */     //   1873: getfield signLines : [Ljava/lang/String;
/*     */     //   1876: invokevirtual setCachedVariable : (Ljava/lang/String;[Ljava/lang/String;)V
/*     */     //   1879: getstatic net/eq2online/macros/compatibility/PrivateFields.damagedBlocks : Lnet/eq2online/macros/compatibility/PrivateFields;
/*     */     //   1882: aload_3
/*     */     //   1883: getfield g : Lckn;
/*     */     //   1886: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   1889: checkcast java/util/HashMap
/*     */     //   1892: astore #15
/*     */     //   1894: aload #15
/*     */     //   1896: ifnull -> 1960
/*     */     //   1899: aload #15
/*     */     //   1901: invokevirtual values : ()Ljava/util/Collection;
/*     */     //   1904: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   1909: astore #16
/*     */     //   1911: aload #16
/*     */     //   1913: invokeinterface hasNext : ()Z
/*     */     //   1918: ifeq -> 1960
/*     */     //   1921: aload #16
/*     */     //   1923: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   1928: checkcast qi
/*     */     //   1931: astore #17
/*     */     //   1933: aload #17
/*     */     //   1935: invokevirtual b : ()Ldt;
/*     */     //   1938: aload #8
/*     */     //   1940: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   1943: ifeq -> 1957
/*     */     //   1946: iconst_0
/*     */     //   1947: aload #17
/*     */     //   1949: invokevirtual c : ()I
/*     */     //   1952: invokestatic max : (II)I
/*     */     //   1955: istore #6
/*     */     //   1957: goto -> 1911
/*     */     //   1960: goto -> 2277
/*     */     //   1963: aload_2
/*     */     //   1964: ifnull -> 2188
/*     */     //   1967: aload #5
/*     */     //   1969: ifnull -> 2188
/*     */     //   1972: aload #5
/*     */     //   1974: getfield a : Lbrv;
/*     */     //   1977: getstatic brv.c : Lbrv;
/*     */     //   1980: if_acmpne -> 2188
/*     */     //   1983: aload #5
/*     */     //   1985: getfield d : Lwv;
/*     */     //   1988: ifnull -> 2188
/*     */     //   1991: aload #5
/*     */     //   1993: getfield d : Lwv;
/*     */     //   1996: instanceof ahd
/*     */     //   1999: ifeq -> 2064
/*     */     //   2002: aload #5
/*     */     //   2004: getfield d : Lwv;
/*     */     //   2007: checkcast ahd
/*     */     //   2010: astore #8
/*     */     //   2012: aload_0
/*     */     //   2013: ldc_w 'HIT'
/*     */     //   2016: ldc_w 'PLAYER'
/*     */     //   2019: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   2022: aload_0
/*     */     //   2023: ldc_w 'HITNAME'
/*     */     //   2026: aload #8
/*     */     //   2028: invokevirtual d_ : ()Ljava/lang/String;
/*     */     //   2031: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   2034: aload_0
/*     */     //   2035: ldc_w 'HITID'
/*     */     //   2038: aload #8
/*     */     //   2040: invokevirtual F : ()I
/*     */     //   2043: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   2046: aload_0
/*     */     //   2047: ldc_w 'HITUUID'
/*     */     //   2050: aload #8
/*     */     //   2052: invokevirtual aJ : ()Ljava/util/UUID;
/*     */     //   2055: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   2058: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   2061: goto -> 2135
/*     */     //   2064: aload #5
/*     */     //   2066: getfield d : Lwv;
/*     */     //   2069: invokestatic b : (Lwv;)Ljava/lang/String;
/*     */     //   2072: astore #8
/*     */     //   2074: aload #8
/*     */     //   2076: ifnonnull -> 2092
/*     */     //   2079: aload #5
/*     */     //   2081: getfield d : Lwv;
/*     */     //   2084: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   2087: invokevirtual getSimpleName : ()Ljava/lang/String;
/*     */     //   2090: astore #8
/*     */     //   2092: aload_0
/*     */     //   2093: ldc_w 'HIT'
/*     */     //   2096: ldc_w 'ENTITY'
/*     */     //   2099: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   2102: aload_0
/*     */     //   2103: ldc_w 'HITNAME'
/*     */     //   2106: aload #8
/*     */     //   2108: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   2111: aload_0
/*     */     //   2112: ldc_w 'HITID'
/*     */     //   2115: aload #5
/*     */     //   2117: getfield d : Lwv;
/*     */     //   2120: invokestatic a : (Lwv;)I
/*     */     //   2123: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   2126: aload_0
/*     */     //   2127: ldc_w 'HITUUID'
/*     */     //   2130: ldc ''
/*     */     //   2132: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   2135: aload_0
/*     */     //   2136: ldc_w 'HITDATA'
/*     */     //   2139: iconst_0
/*     */     //   2140: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   2143: aload_0
/*     */     //   2144: ldc_w 'HITX'
/*     */     //   2147: iconst_0
/*     */     //   2148: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   2151: aload_0
/*     */     //   2152: ldc_w 'HITY'
/*     */     //   2155: iconst_0
/*     */     //   2156: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   2159: aload_0
/*     */     //   2160: ldc_w 'HITZ'
/*     */     //   2163: iconst_0
/*     */     //   2164: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   2167: aload_0
/*     */     //   2168: ldc_w 'HITSIDE'
/*     */     //   2171: ldc_w '?'
/*     */     //   2174: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   2177: aload_0
/*     */     //   2178: getfield hitTracker : Lnet/eq2online/macros/scripting/variable/BlockPropertyTracker;
/*     */     //   2181: aconst_null
/*     */     //   2182: invokevirtual update : (Lbec;)V
/*     */     //   2185: goto -> 2277
/*     */     //   2188: aload_0
/*     */     //   2189: ldc_w 'HIT'
/*     */     //   2192: ldc 'NONE'
/*     */     //   2194: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   2197: aload_0
/*     */     //   2198: ldc_w 'HITNAME'
/*     */     //   2201: ldc_w 'None'
/*     */     //   2204: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   2207: aload_0
/*     */     //   2208: ldc_w 'HITID'
/*     */     //   2211: aconst_null
/*     */     //   2212: invokestatic getItemName : (Lalq;)Ljava/lang/String;
/*     */     //   2215: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   2218: aload_0
/*     */     //   2219: ldc_w 'HITDATA'
/*     */     //   2222: iconst_0
/*     */     //   2223: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   2226: aload_0
/*     */     //   2227: ldc_w 'HITUUID'
/*     */     //   2230: ldc ''
/*     */     //   2232: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   2235: aload_0
/*     */     //   2236: ldc_w 'HITX'
/*     */     //   2239: iconst_0
/*     */     //   2240: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   2243: aload_0
/*     */     //   2244: ldc_w 'HITY'
/*     */     //   2247: iconst_0
/*     */     //   2248: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   2251: aload_0
/*     */     //   2252: ldc_w 'HITZ'
/*     */     //   2255: iconst_0
/*     */     //   2256: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   2259: aload_0
/*     */     //   2260: ldc_w 'HITSIDE'
/*     */     //   2263: ldc_w '?'
/*     */     //   2266: invokevirtual storeVariable : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   2269: aload_0
/*     */     //   2270: getfield hitTracker : Lnet/eq2online/macros/scripting/variable/BlockPropertyTracker;
/*     */     //   2273: aconst_null
/*     */     //   2274: invokevirtual update : (Lbec;)V
/*     */     //   2277: iload #7
/*     */     //   2279: ifeq -> 2297
/*     */     //   2282: aload_0
/*     */     //   2283: aconst_null
/*     */     //   2284: putfield sign : Lbdj;
/*     */     //   2287: aload_0
/*     */     //   2288: ldc_w 'SIGNTEXT'
/*     */     //   2291: getstatic net/eq2online/macros/scripting/variable/providers/VariableProviderPlayer.SIGN_LINES_EMPTY : [Ljava/lang/String;
/*     */     //   2294: invokevirtual setCachedVariable : (Ljava/lang/String;[Ljava/lang/String;)V
/*     */     //   2297: aload_0
/*     */     //   2298: ldc_w 'HITPROGRESS'
/*     */     //   2301: iload #6
/*     */     //   2303: invokevirtual storeVariable : (Ljava/lang/String;I)V
/*     */     //   2306: aload_0
/*     */     //   2307: dup
/*     */     //   2308: getfield signUpdateTicks : I
/*     */     //   2311: iconst_1
/*     */     //   2312: iadd
/*     */     //   2313: putfield signUpdateTicks : I
/*     */     //   2316: aload #4
/*     */     //   2318: invokevirtual b : ()V
/*     */     //   2321: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #162	-> 0
/*     */     //   #163	-> 4
/*     */     //   #164	-> 8
/*     */     //   #165	-> 14
/*     */     //   #167	-> 21
/*     */     //   #168	-> 30
/*     */     //   #169	-> 40
/*     */     //   #171	-> 50
/*     */     //   #172	-> 67
/*     */     //   #174	-> 89
/*     */     //   #176	-> 97
/*     */     //   #178	-> 101
/*     */     //   #179	-> 108
/*     */     //   #180	-> 117
/*     */     //   #181	-> 126
/*     */     //   #182	-> 135
/*     */     //   #184	-> 150
/*     */     //   #185	-> 160
/*     */     //   #186	-> 168
/*     */     //   #188	-> 178
/*     */     //   #189	-> 192
/*     */     //   #190	-> 206
/*     */     //   #192	-> 220
/*     */     //   #194	-> 232
/*     */     //   #195	-> 240
/*     */     //   #196	-> 246
/*     */     //   #197	-> 267
/*     */     //   #198	-> 289
/*     */     //   #201	-> 311
/*     */     //   #202	-> 318
/*     */     //   #204	-> 322
/*     */     //   #206	-> 341
/*     */     //   #207	-> 352
/*     */     //   #210	-> 371
/*     */     //   #211	-> 378
/*     */     //   #212	-> 381
/*     */     //   #214	-> 385
/*     */     //   #215	-> 398
/*     */     //   #217	-> 403
/*     */     //   #218	-> 410
/*     */     //   #219	-> 417
/*     */     //   #222	-> 424
/*     */     //   #223	-> 428
/*     */     //   #224	-> 431
/*     */     //   #226	-> 438
/*     */     //   #227	-> 447
/*     */     //   #228	-> 464
/*     */     //   #229	-> 489
/*     */     //   #230	-> 515
/*     */     //   #233	-> 525
/*     */     //   #234	-> 534
/*     */     //   #235	-> 543
/*     */     //   #236	-> 551
/*     */     //   #237	-> 562
/*     */     //   #238	-> 578
/*     */     //   #239	-> 592
/*     */     //   #240	-> 600
/*     */     //   #241	-> 614
/*     */     //   #242	-> 625
/*     */     //   #243	-> 639
/*     */     //   #244	-> 656
/*     */     //   #245	-> 672
/*     */     //   #246	-> 683
/*     */     //   #247	-> 691
/*     */     //   #248	-> 709
/*     */     //   #249	-> 720
/*     */     //   #250	-> 731
/*     */     //   #251	-> 738
/*     */     //   #252	-> 747
/*     */     //   #253	-> 756
/*     */     //   #254	-> 765
/*     */     //   #255	-> 774
/*     */     //   #256	-> 783
/*     */     //   #257	-> 792
/*     */     //   #258	-> 803
/*     */     //   #259	-> 811
/*     */     //   #260	-> 827
/*     */     //   #261	-> 845
/*     */     //   #262	-> 854
/*     */     //   #263	-> 863
/*     */     //   #264	-> 875
/*     */     //   #265	-> 889
/*     */     //   #267	-> 903
/*     */     //   #268	-> 913
/*     */     //   #269	-> 925
/*     */     //   #270	-> 934
/*     */     //   #271	-> 942
/*     */     //   #272	-> 952
/*     */     //   #275	-> 955
/*     */     //   #276	-> 963
/*     */     //   #277	-> 973
/*     */     //   #278	-> 983
/*     */     //   #279	-> 992
/*     */     //   #280	-> 1001
/*     */     //   #281	-> 1010
/*     */     //   #282	-> 1019
/*     */     //   #283	-> 1027
/*     */     //   #284	-> 1035
/*     */     //   #285	-> 1043
/*     */     //   #286	-> 1051
/*     */     //   #287	-> 1059
/*     */     //   #288	-> 1067
/*     */     //   #289	-> 1076
/*     */     //   #290	-> 1085
/*     */     //   #291	-> 1093
/*     */     //   #292	-> 1101
/*     */     //   #293	-> 1109
/*     */     //   #294	-> 1117
/*     */     //   #295	-> 1127
/*     */     //   #296	-> 1135
/*     */     //   #297	-> 1144
/*     */     //   #298	-> 1154
/*     */     //   #299	-> 1162
/*     */     //   #300	-> 1171
/*     */     //   #301	-> 1180
/*     */     //   #302	-> 1188
/*     */     //   #303	-> 1196
/*     */     //   #305	-> 1204
/*     */     //   #307	-> 1212
/*     */     //   #310	-> 1220
/*     */     //   #311	-> 1228
/*     */     //   #312	-> 1237
/*     */     //   #313	-> 1246
/*     */     //   #314	-> 1255
/*     */     //   #316	-> 1264
/*     */     //   #317	-> 1272
/*     */     //   #319	-> 1307
/*     */     //   #320	-> 1316
/*     */     //   #321	-> 1328
/*     */     //   #322	-> 1338
/*     */     //   #323	-> 1378
/*     */     //   #325	-> 1387
/*     */     //   #326	-> 1396
/*     */     //   #327	-> 1405
/*     */     //   #328	-> 1414
/*     */     //   #329	-> 1426
/*     */     //   #330	-> 1444
/*     */     //   #331	-> 1456
/*     */     //   #332	-> 1468
/*     */     //   #335	-> 1471
/*     */     //   #336	-> 1482
/*     */     //   #337	-> 1492
/*     */     //   #338	-> 1502
/*     */     //   #339	-> 1512
/*     */     //   #340	-> 1520
/*     */     //   #341	-> 1528
/*     */     //   #344	-> 1536
/*     */     //   #345	-> 1542
/*     */     //   #346	-> 1545
/*     */     //   #348	-> 1548
/*     */     //   #349	-> 1556
/*     */     //   #351	-> 1576
/*     */     //   #352	-> 1583
/*     */     //   #353	-> 1594
/*     */     //   #354	-> 1603
/*     */     //   #355	-> 1618
/*     */     //   #356	-> 1631
/*     */     //   #358	-> 1640
/*     */     //   #361	-> 1647
/*     */     //   #362	-> 1661
/*     */     //   #364	-> 1668
/*     */     //   #366	-> 1673
/*     */     //   #367	-> 1683
/*     */     //   #368	-> 1692
/*     */     //   #369	-> 1704
/*     */     //   #370	-> 1713
/*     */     //   #372	-> 1722
/*     */     //   #373	-> 1734
/*     */     //   #374	-> 1746
/*     */     //   #375	-> 1758
/*     */     //   #377	-> 1777
/*     */     //   #379	-> 1786
/*     */     //   #381	-> 1794
/*     */     //   #382	-> 1805
/*     */     //   #384	-> 1813
/*     */     //   #386	-> 1831
/*     */     //   #387	-> 1836
/*     */     //   #389	-> 1845
/*     */     //   #390	-> 1850
/*     */     //   #391	-> 1855
/*     */     //   #392	-> 1860
/*     */     //   #395	-> 1865
/*     */     //   #396	-> 1868
/*     */     //   #400	-> 1879
/*     */     //   #402	-> 1894
/*     */     //   #404	-> 1899
/*     */     //   #406	-> 1933
/*     */     //   #407	-> 1946
/*     */     //   #408	-> 1957
/*     */     //   #410	-> 1960
/*     */     //   #411	-> 1963
/*     */     //   #413	-> 1991
/*     */     //   #415	-> 2002
/*     */     //   #417	-> 2012
/*     */     //   #418	-> 2022
/*     */     //   #419	-> 2034
/*     */     //   #420	-> 2046
/*     */     //   #421	-> 2061
/*     */     //   #424	-> 2064
/*     */     //   #426	-> 2074
/*     */     //   #427	-> 2079
/*     */     //   #429	-> 2092
/*     */     //   #430	-> 2102
/*     */     //   #431	-> 2111
/*     */     //   #432	-> 2126
/*     */     //   #435	-> 2135
/*     */     //   #437	-> 2143
/*     */     //   #438	-> 2151
/*     */     //   #439	-> 2159
/*     */     //   #440	-> 2167
/*     */     //   #442	-> 2177
/*     */     //   #446	-> 2188
/*     */     //   #447	-> 2197
/*     */     //   #448	-> 2207
/*     */     //   #449	-> 2218
/*     */     //   #450	-> 2226
/*     */     //   #452	-> 2235
/*     */     //   #453	-> 2243
/*     */     //   #454	-> 2251
/*     */     //   #455	-> 2259
/*     */     //   #457	-> 2269
/*     */     //   #460	-> 2277
/*     */     //   #462	-> 2282
/*     */     //   #463	-> 2287
/*     */     //   #466	-> 2297
/*     */     //   #467	-> 2306
/*     */     //   #468	-> 2316
/*     */     //   #469	-> 2321
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   352	19	13	playerChunk	Lbfh;
/*     */     //   410	14	16	playerGameMode	Larc;
/*     */     //   117	835	5	posX	I
/*     */     //   126	826	6	posY	I
/*     */     //   135	817	7	posZ	I
/*     */     //   150	802	8	pos	Ldt;
/*     */     //   160	792	9	yaw	I
/*     */     //   168	784	10	realYaw	I
/*     */     //   178	774	11	pitch	I
/*     */     //   322	630	12	biomeName	Ljava/lang/String;
/*     */     //   381	571	13	gameMode	I
/*     */     //   385	567	14	gameModeName	Ljava/lang/String;
/*     */     //   398	554	15	playerInfo	Lces;
/*     */     //   428	524	16	vehicle	Ljava/lang/String;
/*     */     //   431	521	17	vehicleHealth	F
/*     */     //   913	39	18	difficulty	Lvu;
/*     */     //   925	27	19	diff	I
/*     */     //   1316	152	5	currentItem	Lamj;
/*     */     //   1328	140	6	itemCode	Ljava/lang/String;
/*     */     //   1338	130	7	idFromItem	Ljava/lang/String;
/*     */     //   1378	90	8	itemId	Ljava/lang/String;
/*     */     //   1661	7	15	stack	Lamj;
/*     */     //   1805	74	15	teSign	Lbcm;
/*     */     //   1933	24	17	damage	Lqi;
/*     */     //   1583	377	8	blockPos	Ldt;
/*     */     //   1594	366	9	blockState	Lbec;
/*     */     //   1603	357	10	block	Latr;
/*     */     //   1618	342	11	actualState	Lbec;
/*     */     //   1631	329	12	item	Lalq;
/*     */     //   1640	320	13	blockMeta	I
/*     */     //   1647	313	14	displayName	Ljava/lang/String;
/*     */     //   1894	66	15	damagedBlocks	Ljava/util/HashMap;
/*     */     //   2012	49	8	player	Lahd;
/*     */     //   2074	61	8	entityName	Ljava/lang/String;
/*     */     //   0	2322	0	this	Lnet/eq2online/macros/scripting/variable/providers/VariableProviderPlayer;
/*     */     //   0	2322	1	clock	Z
/*     */     //   4	2318	2	thePlayer	Lcio;
/*     */     //   8	2314	3	mc	Lbsu;
/*     */     //   14	2308	4	profiler	Luw;
/*     */     //   1542	780	5	objectHit	Lbru;
/*     */     //   1545	777	6	blockDamage	I
/*     */     //   1548	774	7	clearSign	Z
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   1894	66	15	damagedBlocks	Ljava/util/HashMap<Ljava/lang/Integer;Lqi;>;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   1647	1668	1671	java/lang/Exception
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readSignLine(int pos) {
/* 473 */     this.signLines[pos] = (this.sign.a[pos] != null) ? this.sign.a[pos].d() : "";
/*     */   }
/*     */ 
/*     */   
/*     */   private vu getDifficulty(bsu mc, cio thePlayer, dt pos) {
/* 478 */     vu difficulty = mc.f.E(pos);
/*     */     
/* 480 */     if (mc.D() && mc.F() != null) {
/*     */       
/* 482 */       qw var8 = mc.F().an().a(mc.h.aJ());
/*     */       
/* 484 */       if (var8 != null)
/*     */       {
/* 486 */         difficulty = var8.o.E(new dt((wv)var8));
/*     */       }
/*     */     } 
/*     */     
/* 490 */     return difficulty;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/* 496 */     return getCachedValue(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateArmourSlotVariables(cio thePlayer, int armourSlot, String armourName) {
/* 508 */     amj armourItem = (thePlayer != null) ? thePlayer.bg.e(armourSlot) : null;
/* 509 */     storeVariable(armourName + "ID", (armourItem != null) ? Macros.getItemName(armourItem.b()) : "");
/* 510 */     storeVariable(armourName + "NAME", (armourItem != null) ? armourItem.q() : "None");
/* 511 */     storeVariable(armourName + "DURABILITY", (armourItem != null) ? (armourItem.j() - armourItem.i()) : 0);
/* 512 */     storeVariable(armourName + "DAMAGE", (armourItem != null) ? armourItem.j() : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getNameForDimension(int dimension, aqu worldObj) {
/* 524 */     switch (dimension) {
/*     */       case -1:
/* 526 */         return "NETHER";
/* 527 */       case 0: return "SURFACE";
/* 528 */       case 1: return "END";
/* 529 */     }  return (worldObj == null || worldObj.t == null) ? "UNKNOWN" : ("" + worldObj.t.k()).toUpperCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getNameOfCurrentGuiScreen() {
/* 540 */     bxf currentScreen = (bsu.z()).m;
/* 541 */     if (currentScreen == null) return "NONE";
/*     */     
/* 543 */     Class<? extends bxf> screenClass = bxf.class;
/* 544 */     Class<? extends bxf> currentClass = (Class)currentScreen.getClass();
/*     */ 
/*     */     
/* 547 */     for (Map.Entry<Class<? extends bxf>, String> classMapping : guiClassNameMap.entrySet()) {
/*     */       
/* 549 */       if (((Class)classMapping.getKey()).isAssignableFrom(currentClass) && !((Class)classMapping.getKey()).isAssignableFrom(screenClass)) {
/* 550 */         screenClass = classMapping.getKey();
/*     */       }
/*     */     } 
/*     */     
/* 554 */     if (screenClass.equals(bxf.class)) {
/* 555 */       return currentScreen.getClass().getSimpleName().toUpperCase();
/*     */     }
/* 557 */     return guiClassNameMap.get(screenClass);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getSideName(int sideHit) {
/* 562 */     if (sideHit > -1 && sideHit < 7) {
/* 563 */       return BLOCK_SIDES[sideHit];
/*     */     }
/* 565 */     return "?";
/*     */   }
/*     */   
/*     */   public void onInit() {}
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\variable\providers\VariableProviderPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */