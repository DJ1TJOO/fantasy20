package nl.fantasynetworkmc.fantasy20;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import nl.fantasynetworkmc.fantasy20.setup.ClientProxy;
import nl.fantasynetworkmc.fantasy20.setup.GameEvents;
import nl.fantasynetworkmc.fantasy20.setup.IProxy;
import nl.fantasynetworkmc.fantasy20.setup.ModSetup;
import nl.fantasynetworkmc.fantasy20.setup.RegistryEvents;
import nl.fantasynetworkmc.fantasy20.setup.ServerProxy;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("fantasy20")
public class Fantasy20 {

	public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
	public static ModSetup setup = new ModSetup();
	public static String MODID = "fantasy20";
	public static final Logger LOGGER = LogManager.getLogger();

    public Fantasy20() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(RegistryEvents.class); 
        MinecraftForge.EVENT_BUS.register(GameEvents.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
    	setup.init();
    	proxy.init();
    }
}
