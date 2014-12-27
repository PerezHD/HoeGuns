package co.infnet.hoeguns;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class HoeGuns extends JavaPlugin implements Listener{
	public final Logger logger = Logger.getLogger("Minecraft");
	public static HoeGuns plugin;
	
	public HoeGuns instance;
	public HoeGuns getInstance(){
		return instance;
	}
	
	@Override
	public void onDisable() {
		instance = null;
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
	}
	
	@Override
	public void onEnable() {
		instance = this;
		this.saveDefaultConfig();
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " has been enabled!");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	HashMap<String, Boolean> GunsEnabled = new HashMap<String, Boolean>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event){ 
		Player p = event.getPlayer();
		String uuid = p.getUniqueId().toString();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
			Snowball snowball = (Snowball) p.launchProjectile(Snowball.class);
			if(p.getItemInHand().getType() == Material.DIAMOND_HOE || p.getItemInHand().getType() == Material.GOLD_HOE || p.getItemInHand().getType() == Material.IRON_HOE || p.getItemInHand().getType() == Material.STONE_HOE || p.getItemInHand().getType() == Material.WOOD_HOE){
				if(GunsEnabled.get(uuid)){
					snowball.setVelocity(p.getEyeLocation().getDirection().multiply(10D));
					snowball.setShooter(p);
				}	
			}
		}
	}
	
	public boolean onCommand(CommandSender s, Command cmd, String c, String args[]){
		Player p = (Player) s;
		String uuid = p.getUniqueId().toString();
		if(c.equalsIgnoreCase("hw")){
			if(p.hasPermission("hg.use")){
				if(!GunsEnabled.get(uuid)){
					GunsEnabled.put(uuid, true);
					p.sendMessage("Hoe Guns enabled!");
				}else{
					GunsEnabled.put(uuid, false);
				}
			}	
		}
		return true;
	}
}	