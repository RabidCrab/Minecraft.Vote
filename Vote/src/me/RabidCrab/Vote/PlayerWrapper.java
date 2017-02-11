package me.RabidCrab.Vote;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

/**
 * Used to route all the permission methods to an op account so they always succeed, but keeping all the other methods specific to the vote starter so that
 * any plugin that executes a player specific method will change the voting players status, and not the mimicked ops
 * @author Rabid
 *
 */
public class PlayerWrapper implements Player
{
    Player caller;
    String opName;
    
    public PlayerWrapper(String opName)
    {
        this.opName = opName;
    }
    
    public void setCaller(Player player)
    {
        this.caller = player;
    }
    
    @Override
    public PlayerInventory getInventory()
    {
        return caller.getInventory();
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getItemInHand()
    {
        return caller.getItemInHand();
    }

    @Override
    public String getName()
    {
        return opName;
    }

    @Override
    public int getSleepTicks()
    {
        return caller.getSleepTicks();
    }

    @Override
    public boolean isSleeping()
    {
        return caller.isSleeping();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setItemInHand(ItemStack arg0)
    {
        caller.setItemInHand(arg0);
    }

    @Override
    public double getEyeHeight()
    {
        return caller.getEyeHeight();
    }

    @Override
    public double getEyeHeight(boolean arg0)
    {
        return caller.getEyeHeight(arg0);
    }

    @Override
    public Location getEyeLocation()
    {
        return caller.getEyeLocation();
    }

    @Override
    public double getHealth()
    {
        return caller.getHealth();
    }

    @Override
    public double getLastDamage()
    {
        return caller.getLastDamage();
    }

    @Deprecated
    @Override
    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> arg0, int arg1)
    {
        return caller.getLastTwoTargetBlocks(arg0, arg1);
    }

    @Deprecated
    @Override
    public List<Block> getLineOfSight(HashSet<Byte> arg0, int arg1)
    {
        return caller.getLineOfSight(arg0, arg1);
    }

    @Override
    public int getMaximumAir()
    {
        return caller.getMaximumAir();
    }

    @Override
    public int getMaximumNoDamageTicks()
    {
        return caller.getMaximumNoDamageTicks();
    }

    @Override
    public int getNoDamageTicks()
    {
        return caller.getNoDamageTicks();
    }

    @Override
    public int getRemainingAir()
    {
        return caller.getRemainingAir();
    }

    @Deprecated
    @Override
    public Block getTargetBlock(HashSet<Byte> arg0, int arg1)
    {
        return caller.getTargetBlock(arg0, arg1);
    }

    @Override
    public Entity getVehicle()
    {
        return caller.getVehicle();
    }

    @Override
    public boolean isInsideVehicle()
    {
        return caller.isInsideVehicle();
    }

    @Override
    public boolean leaveVehicle()
    {
        return caller.leaveVehicle();
    }

    @Override
    public void setMaximumAir(int arg0)
    {
        caller.setMaximumAir(arg0);
    }

    @Override
    public void setMaximumNoDamageTicks(int arg0)
    {
        caller.setMaximumNoDamageTicks(arg0);
    }

    @Override
    public void setNoDamageTicks(int arg0)
    {
        caller.setNoDamageTicks(arg0);
    }

    @Override
    public void setRemainingAir(int arg0)
    {
        caller.setRemainingAir(arg0);
    }

    @Override
    public boolean eject()
    {
        return caller.eject();
    }

    @Override
    public int getEntityId()
    {
        return caller.getEntityId();
    }

    @Override
    public float getFallDistance()
    {
        return caller.getFallDistance();
    }

    @Override
    public int getFireTicks()
    {
        return caller.getFireTicks();
    }

    @Override
    public EntityDamageEvent getLastDamageCause()
    {
        return caller.getLastDamageCause();
    }

    @Override
    public Location getLocation()
    {
        return caller.getLocation();
    }

    @Override
    public int getMaxFireTicks()
    {
        return caller.getMaxFireTicks();
    }

    @Override
    public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2)
    {
        return caller.getNearbyEntities(arg0, arg1, arg2);
    }

    @Override
    public Entity getPassenger()
    {
        return caller.getPassenger();
    }

    @Override
    public Server getServer()
    {
        return caller.getServer();
    }

    @Override
    public UUID getUniqueId()
    {
        return caller.getUniqueId();
    }

    @Override
    public Vector getVelocity()
    {
        return caller.getVelocity();
    }

    @Override
    public World getWorld()
    {
        return caller.getWorld();
    }

    @Override
    public boolean isDead()
    {
        return caller.isDead();
    }

    @Override
    public boolean isEmpty()
    {
        return caller.isEmpty();
    }

    @Override
    public void remove()
    {
        caller.remove();
    }

    @Override
    public void setFallDistance(float arg0)
    {
        caller.setFallDistance(arg0);
    }

    @Override
    public void setFireTicks(int arg0)
    {
        caller.setFireTicks(arg0);
    }

    @Override
    public void setLastDamageCause(EntityDamageEvent arg0)
    {
        caller.setLastDamageCause(arg0);
    }

    @Override
    public boolean setPassenger(Entity arg0)
    {
        return caller.setPassenger(arg0);
    }

    @Override
    public void setVelocity(Vector arg0)
    {
        caller.setVelocity(arg0);
    }

    @Override
    public boolean teleport(Location arg0)
    {
        return caller.teleport(arg0);
    }

    @Override
    public boolean teleport(Entity arg0)
    {
        return caller.teleport(arg0);
    }

    @Override
    public boolean isOp()
    {
        return true;
    }

    @Override
    public void sendMessage(String arg0)
    {
        caller.sendMessage(arg0);
    }

    @Override
    public void awardAchievement(Achievement arg0)
    {
        caller.awardAchievement(arg0);
    }

    @Override
    public void chat(String arg0)
    {
        caller.chat(arg0);
    }

    @Override
    public InetSocketAddress getAddress()
    {
        return caller.getAddress();
    }

    @Override
    public Location getCompassTarget()
    {
        return caller.getCompassTarget();
    }

    @Override
    public String getDisplayName()
    {
        return opName;
    }

    @Override
    public void incrementStatistic(Statistic arg0)
    {
        caller.incrementStatistic(arg0);
    }

    @Override
    public void incrementStatistic(Statistic arg0, int arg1)
    {
        caller.incrementStatistic(arg0, arg1);
    }

    @Override
    public void incrementStatistic(Statistic arg0, Material arg1)
    {
        caller.incrementStatistic(arg0, arg1);
    }

    @Override
    public void incrementStatistic(Statistic arg0, Material arg1, int arg2)
    {
        caller.incrementStatistic(arg0, arg1, arg2);
    }

    @Override
    public boolean isOnline()
    {
        return caller.isOnline();
    }

    @Override
    public boolean isSleepingIgnored()
    {
        return caller.isSleepingIgnored();
    }

    @Override
    public boolean isSneaking()
    {
        return caller.isSneaking();
    }

    @Override
    public void kickPlayer(String arg0)
    {
        caller.kickPlayer(arg0);
    }

    @Override
    public void loadData()
    {
        caller.loadData();
    }

    @Override
    public boolean performCommand(String arg0)
    {
        return caller.performCommand(arg0);
    }

    @Override
    @Deprecated
    public void playEffect(Location arg0, Effect arg1, int arg2)
    {
        caller.playEffect(arg0, arg1, arg2);
    }

    @Override
    @Deprecated
    public void playNote(Location arg0, byte arg1, byte arg2)
    {
        caller.playNote(arg0, arg1, arg2);
    }

    @Override
    public void saveData()
    {
        caller.saveData();
    }

    @Deprecated
    @Override
    public void sendBlockChange(Location arg0, Material arg1, byte arg2)
    {
        caller.sendBlockChange(arg0, arg1, arg2);
    }

    @Deprecated
    @Override
    public void sendBlockChange(Location arg0, int arg1, byte arg2)
    {
        caller.sendBlockChange(arg0, arg1, arg2);
    }

    @Deprecated
    @Override
    public boolean sendChunkChange(Location arg0, int arg1, int arg2, int arg3,
            byte[] arg4)
    {
        return caller.sendChunkChange(arg0, arg1, arg2, arg3, arg4);
    }

    @Override
    public void sendRawMessage(String arg0)
    {
        caller.sendRawMessage(arg0);
    }

    @Override
    public void setCompassTarget(Location arg0)
    {
        caller.setCompassTarget(arg0);
    }

    @Override
    public void setDisplayName(String arg0)
    {
        caller.setDisplayName(arg0);
    }

    @Override
    public void setSleepingIgnored(boolean arg0)
    {
        caller.setSleepingIgnored(arg0);
    }

    @Override
    public void setSneaking(boolean arg0)
    {
        caller.setSneaking(arg0);
    }
    
    @Deprecated
    @Override
    public void updateInventory()
    {
        caller.updateInventory();
    }

    @Override
    public long getPlayerTime()
    {
        return caller.getPlayerTime();
    }

    @Override
    public long getPlayerTimeOffset()
    {
        return caller.getPlayerTimeOffset();
    }

    @Override
    public boolean isPlayerTimeRelative()
    {
        return caller.isPlayerTimeRelative();
    }

    @Override
    public void playNote(Location arg0, Instrument arg1, Note arg2)
    {
        caller.playNote(arg0, arg1, arg2);
    }

    @Override
    public void resetPlayerTime()
    {
        caller.resetPlayerTime();
    }

    @Override
    public void setPlayerTime(long arg0, boolean arg1)
    {
        caller.setPlayerTime(arg0, arg1);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin arg0)
    {
        return caller.addAttachment(arg0);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin arg0, int arg1)
    {
        return caller.addAttachment(arg0, arg1);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin arg0, String arg1,
            boolean arg2)
    {
        return caller.addAttachment(arg0, arg1, arg2);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin arg0, String arg1,
            boolean arg2, int arg3)
    {
        return caller.addAttachment(arg0, arg1, arg2, arg3);
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions()
    {
        return caller.getEffectivePermissions();
    }

    @Override
    public boolean hasPermission(String arg0)
    {
        return true;
    }

    @Override
    public boolean hasPermission(Permission arg0)
    {
        return true;
    }

    @Override
    public boolean isPermissionSet(String arg0)
    {
        return caller.isPermissionSet(arg0);
    }

    @Override
    public boolean isPermissionSet(Permission arg0)
    {
        return caller.isPermissionSet(arg0);
    }

    @Override
    public void recalculatePermissions()
    {
        caller.recalculatePermissions();
    }

    @Override
    public void removeAttachment(PermissionAttachment arg0)
    {
        caller.removeAttachment(arg0);
    }

    @Override
    public void setOp(boolean arg0)
    {
        caller.setOp(arg0);
    }

    @Override
    public void sendMap(MapView arg0)
    {
        caller.sendMap(arg0);
    }

    @Override
    public void closeInventory()
    {
        
        caller.closeInventory();
    }

    @Override
    public int getExpToLevel()
    {
        
        return caller.getExpToLevel();
    }

    @Override
    public GameMode getGameMode()
    {
        
        return caller.getGameMode();
    }

    @Override
    public ItemStack getItemOnCursor()
    {
        
        return caller.getItemOnCursor();
    }

    @Override
    public InventoryView getOpenInventory()
    {
        
        return caller.getOpenInventory();
    }

    @Override
    public boolean isBlocking()
    {
        
        return caller.isBlocking();
    }

    @Override
    public InventoryView openEnchanting(Location arg0, boolean arg1)
    {
        
        return caller.openEnchanting(arg0, arg1);
    }

    @Override
    public InventoryView openInventory(Inventory arg0)
    {
        
        return caller.openInventory(arg0);
    }

    @Override
    public void openInventory(InventoryView arg0)
    {
        
        caller.openInventory(arg0);
    }

    @Override
    public InventoryView openWorkbench(Location arg0, boolean arg1)
    {
        
        return caller.openWorkbench(arg0, arg1);
    }

    @Override
    public void setGameMode(GameMode arg0)
    {
        
        caller.setGameMode(arg0);
    }

    @Override
    public void setItemOnCursor(ItemStack arg0)
    {
        
        caller.setItemOnCursor(arg0);
    }

    @Override
    public boolean setWindowProperty(Property arg0, int arg1)
    {
        
        return caller.setWindowProperty(arg0, arg1);
    }

    @Override
    public boolean addPotionEffect(PotionEffect arg0)
    {
        
        return caller.addPotionEffect(arg0);
    }

    @Override
    public boolean addPotionEffect(PotionEffect arg0, boolean arg1)
    {
        
        return caller.addPotionEffect(arg0, arg1);
    }

    @Override
    public boolean addPotionEffects(Collection<PotionEffect> arg0)
    {
        
        return caller.addPotionEffects(arg0);
    }

    @Override
    public Collection<PotionEffect> getActivePotionEffects()
    {
        
        return caller.getActivePotionEffects();
    }

    @Override
    public Player getKiller()
    {
        
        return caller.getKiller();
    }

    @Override
    public double getMaxHealth()
    {
        
        return caller.getMaxHealth();
    }

    @Override
    public boolean hasLineOfSight(Entity arg0)
    {
        
        return caller.hasLineOfSight(arg0);
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType arg0)
    {
        
        return caller.hasPotionEffect(arg0);
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> arg0)
    {
        
        return caller.launchProjectile(arg0);
    }

    @Override
    public void removePotionEffect(PotionEffectType arg0)
    {
        
        caller.removePotionEffect(arg0);
    }

    @Override
    public int getTicksLived()
    {
        
        return caller.getTicksLived();
    }

    @Override
    public EntityType getType()
    {
        
        return caller.getType();
    }

    @Override
    public boolean isValid()
    {
        
        return caller.isValid();
    }

    @Override
    public void playEffect(EntityEffect arg0)
    {
        
        caller.playEffect(arg0);
    }

    @Override
    public void setTicksLived(int arg0)
    {
        
        caller.setTicksLived(arg0);
    }

    @Override
    public boolean teleport(Location arg0, TeleportCause arg1)
    {
        
        return caller.teleport(arg0, arg1);
    }

    @Override
    public boolean teleport(Entity arg0, TeleportCause arg1)
    {
        
        return caller.teleport(arg0, arg1);
    }

    @Override
    public List<MetadataValue> getMetadata(String arg0)
    {
        
        return caller.getMetadata(arg0);
    }

    @Override
    public boolean hasMetadata(String arg0)
    {
        
        return caller.hasMetadata(arg0);
    }

    @Override
    public void removeMetadata(String arg0, Plugin arg1)
    {
        
        caller.removeMetadata(arg0, arg1);
    }

    @Override
    public void setMetadata(String arg0, MetadataValue arg1)
    {
        
        caller.setMetadata(arg0, arg1);
    }

    @Override
    public void abandonConversation(Conversation arg0)
    {
        
        caller.abandonConversation(arg0);
    }

    @Override
    public void abandonConversation(Conversation arg0,
            ConversationAbandonedEvent arg1)
    {
        
        caller.abandonConversation(arg0, arg1);
    }

    @Override
    public void acceptConversationInput(String arg0)
    {
        
        caller.acceptConversationInput(arg0);
    }

    @Override
    public boolean beginConversation(Conversation arg0)
    {
        
        return caller.beginConversation(arg0);
    }

    @Override
    public boolean isConversing()
    {
        
        return caller.isConversing();
    }

    @Override
    public void sendMessage(String[] arg0)
    {
        
        caller.sendMessage(arg0);
    }

    @Override
    public long getFirstPlayed()
    {
        
        return caller.getFirstPlayed();
    }

    @Override
    public long getLastPlayed()
    {
        
        return caller.getLastPlayed();
    }

    @Override
    public Player getPlayer()
    {
        return caller.getPlayer();
    }

    @Override
    public boolean hasPlayedBefore()
    {
        return caller.hasPlayedBefore();
    }

    @Override
    public boolean isBanned()
    {
        return caller.isBanned();
    }

    @Override
    public boolean isWhitelisted()
    {
        return caller.isWhitelisted();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBanned(boolean arg0)
    {
        caller.setBanned(arg0);
    }

    @Override
    public void setWhitelisted(boolean arg0)
    {
        caller.setWhitelisted(arg0);
    }

    @Override
    public Map<String, Object> serialize()
    {
        return caller.serialize();
    }

    @Override
    public Set<String> getListeningPluginChannels()
    {
        return caller.getListeningPluginChannels();
    }

    @Override
    public void sendPluginMessage(Plugin arg0, String arg1, byte[] arg2)
    {
        caller.sendPluginMessage(arg0, arg1, arg2);
    }

    @Override
    public boolean canSee(Player arg0)
    {
        return caller.canSee(arg0);
    }

    @Override
    public boolean getAllowFlight()
    {
        return caller.getAllowFlight();
    }

    @Override
    public Location getBedSpawnLocation()
    {
        return caller.getBedSpawnLocation();
    }

    @Override
    public float getExhaustion()
    {
        return caller.getExhaustion();
    }

    @Override
    public float getExp()
    {
        return caller.getExp();
    }

    @Override
    public int getFoodLevel()
    {
        return caller.getFoodLevel();
    }

    @Override
    public int getLevel()
    {
        return caller.getLevel();
    }

    @Override
    public String getPlayerListName()
    {
        return caller.getPlayerListName();
    }

    @Override
    public float getSaturation()
    {
        return caller.getSaturation();
    }

    @Override
    public int getTotalExperience()
    {
        return caller.getTotalExperience();
    }

    @Override
    public void giveExp(int arg0)
    {
        caller.giveExp(arg0);
    }

    @Override
    public void hidePlayer(Player arg0)
    {
        caller.hidePlayer(arg0);
    }

    @Override
    public boolean isFlying()
    {
        return caller.isFlying();
    }

    @Override
    public boolean isSprinting()
    {
        return caller.isSprinting();
    }

    @Override
    public <T> void playEffect(Location arg0, Effect arg1, T arg2)
    {
        caller.playEffect(arg0, arg1, arg2);
    }

    @Override
    public void setAllowFlight(boolean arg0)
    {
        caller.setAllowFlight(arg0);
    }

    @Override
    public void setBedSpawnLocation(Location arg0)
    {
        caller.setBedSpawnLocation(arg0);
    }

    @Override
    public void setExhaustion(float arg0)
    {
        caller.setExhaustion(arg0);
    }

    @Override
    public void setExp(float arg0)
    {
        caller.setExp(arg0);
    }

    @Override
    public void setFlying(boolean arg0)
    {
        caller.setFlying(arg0);
    }

    @Override
    public void setFoodLevel(int arg0)
    {
        caller.setFoodLevel(arg0);
    }

    @Override
    public void setLevel(int arg0)
    {
        caller.setLevel(arg0);
    }

    @Override
    public void setPlayerListName(String arg0)
    {
        caller.setPlayerListName(arg0);
    }

    @Override
    public void setSaturation(float arg0)
    {
        caller.setSaturation(arg0);
    }

    @Override
    public void setSprinting(boolean arg0)
    {
        caller.setSprinting(arg0);
    }

    @Override
    public void setTotalExperience(int arg0)
    {
        caller.setTotalExperience(arg0);
    }

    @Override
    public void showPlayer(Player arg0)
    {
        caller.showPlayer(arg0);
    }

    @Override
    public float getFlySpeed()
    {
        return caller.getFlySpeed();
    }

    @Override
    public float getWalkSpeed()
    {
        return caller.getWalkSpeed();
    }

    @Override
    public void setFlySpeed(float arg0) throws IllegalArgumentException
    {
        caller.setFlySpeed(arg0);
    }

    @Override
    public void setWalkSpeed(float arg0) throws IllegalArgumentException
    {
        caller.setWalkSpeed(arg0);
    }

    @Override
    public Inventory getEnderChest()
    {
        return caller.getEnderChest();
    }

    @Override
    public void playSound(Location arg0, Sound arg1, float arg2, float arg3)
    {
        caller.playSound(arg0, arg1, arg2, arg3);
    }

    @Override
    public void setBedSpawnLocation(Location arg0, boolean arg1)
    {
        caller.setBedSpawnLocation(arg0, arg1);
    }

    @Override
    public void giveExpLevels(int arg0)
    {
        caller.giveExpLevels(arg0);
    }
    
	@Override
	public boolean getCanPickupItems() {
		return caller.getCanPickupItems();
	}

	@Override
	public EntityEquipment getEquipment() {
		return caller.getEquipment();
	}

	@Override
	public boolean getRemoveWhenFarAway() {
		return caller.getRemoveWhenFarAway();
	}

	@Override
	public void setCanPickupItems(boolean arg0) {
		caller.setCanPickupItems(arg0);
	}

	@Override
	public void setRemoveWhenFarAway(boolean arg0) {
		caller.setRemoveWhenFarAway(arg0);
	}

	@Override
	public Location getLocation(Location arg0) {
		return caller.getLocation(arg0);
	}

	@Override
	public void resetMaxHealth() {
		caller.resetMaxHealth();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setTexturePack(String arg0) {
		caller.setTexturePack(arg0);
	}

    @Override
    public String getCustomName()
    {
        return caller.getCustomName();
    }

    @Override
    public boolean isCustomNameVisible()
    {
        return caller.isCustomNameVisible();
    }

    @Override
    public void setCustomName(String arg0)
    {
        caller.setCustomName(arg0);
    }

    @Override
    public void setCustomNameVisible(boolean arg0)
    {
        caller.setCustomNameVisible(arg0);
    }

    @Override
    public WeatherType getPlayerWeather()
    {
        return caller.getPlayerWeather();
    }

    @Override
    @Deprecated
    public boolean isOnGround()
    {
        return caller.isOnGround();
    }

    @Override
    public void resetPlayerWeather()
    {
        caller.resetPlayerWeather();
    }

    @Override
    public void setPlayerWeather(WeatherType arg0)
    {
        caller.setPlayerWeather(arg0);
    }

    @Override
    public Scoreboard getScoreboard()
    {
        return caller.getScoreboard();
    }

    @Override
    public void setScoreboard(Scoreboard arg0) throws IllegalArgumentException,
            IllegalStateException
    {
        caller.setScoreboard(arg0);
    }

    @Override
    @Deprecated
    public int _INVALID_getLastDamage()
    {
        // TODO Auto-generated method stub
        return caller._INVALID_getLastDamage();
    }

    @Override
    @Deprecated
    public void _INVALID_setLastDamage(int arg0)
    {
        // TODO Auto-generated method stub
    	caller._INVALID_setLastDamage(arg0);
    }

    @Override
    public void setLastDamage(double arg0)
    {
        // TODO Auto-generated method stub
        caller.setLastDamage(arg0);
    }

    @Override
    @Deprecated
    public void _INVALID_damage(int arg0)
    {
        // TODO Auto-generated method stub
    	caller._INVALID_damage(arg0);
    }

    @Override
    @Deprecated
    public void _INVALID_damage(int arg0, Entity arg1)
    {
        // TODO Auto-generated method stub
    	caller._INVALID_damage(arg0, arg1);
    }

    @Override
    @Deprecated
    public int _INVALID_getHealth()
    {
        // TODO Auto-generated method stub
        return caller._INVALID_getHealth();
    }

    @Override
    @Deprecated
    public int _INVALID_getMaxHealth()
    {
        // TODO Auto-generated method stub
    	return caller._INVALID_getMaxHealth();
    }

    @Override
    @Deprecated
    public void _INVALID_setHealth(int arg0)
    {
        caller._INVALID_setHealth(arg0);
    }

    @Override
    @Deprecated
    public void _INVALID_setMaxHealth(int arg0)
    {
        caller._INVALID_setMaxHealth(arg0);
    }

    @Override
    public void damage(double arg0)
    {
        caller.damage(arg0);
    }

    @Override
    public void damage(double arg0, Entity arg1)
    {
        caller.damage(arg0, arg1);
    }

    @Override
    public void setHealth(double arg0)
    {
        caller.setHealth(arg0);
    }

    @Override
    public void setMaxHealth(double arg0)
    {
        caller.setMaxHealth(arg0);
    }

    @Override
    public Entity getLeashHolder() throws IllegalStateException
    {
        return caller.getLeashHolder();
    }

    @Override
    public boolean isLeashed()
    {
        return caller.isLeashed();
    }

    @Override
    public boolean setLeashHolder(Entity arg0)
    {
        return caller.setLeashHolder(arg0);
    }

    @Override
    public double getHealthScale()
    {
        return caller.getHealthScale();
    }

    @Override
    public boolean isHealthScaled()
    {
        return caller.isHealthScaled();
    }

    @Override
    @Deprecated
    public void playSound(Location arg0, String arg1, float arg2, float arg3)
    {
        caller.playSound(arg0, arg1, arg2, arg3);
    }

    @Override
    public void setHealthScale(double arg0) throws IllegalArgumentException
    {
        caller.setHealthScale(arg0);
    }

    @Override
    public void setHealthScaled(boolean arg0)
    {
        caller.setHealthScaled(arg0);
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> arg0,
            Vector arg1)
    {
        return caller.launchProjectile(arg0, arg1);
    }

    @Override
    public void decrementStatistic(Statistic arg0)
            throws IllegalArgumentException
    {
        caller.decrementStatistic(arg0);
    }

    @Override
    public void decrementStatistic(Statistic arg0, int arg1)
            throws IllegalArgumentException
    {
        caller.decrementStatistic(arg0, arg1);
    }

    @Override
    public void decrementStatistic(Statistic arg0, Material arg1)
            throws IllegalArgumentException
    {
        caller.decrementStatistic(arg0, arg1);
    }

    @Override
    public void decrementStatistic(Statistic arg0, EntityType arg1)
            throws IllegalArgumentException
    {
        caller.decrementStatistic(arg0, arg1);
    }

    @Override
    public void decrementStatistic(Statistic arg0, Material arg1, int arg2)
            throws IllegalArgumentException
    {
        caller.decrementStatistic(arg0, arg1, arg2);
    }

    @Override
    public void decrementStatistic(Statistic arg0, EntityType arg1, int arg2)
    {
        caller.decrementStatistic(arg0, arg1, arg2);
    }

    @Override
    public int getStatistic(Statistic arg0) throws IllegalArgumentException
    {
        return caller.getStatistic(arg0);
    }

    @Override
    public int getStatistic(Statistic arg0, Material arg1)
            throws IllegalArgumentException
    {
        return caller.getStatistic(arg0, arg1);
    }

    @Override
    public int getStatistic(Statistic arg0, EntityType arg1)
            throws IllegalArgumentException
    {
        return caller.getStatistic(arg0, arg1);
    }

    @Override
    public boolean hasAchievement(Achievement arg0)
    {
        return caller.hasAchievement(arg0);
    }

    @Override
    public void incrementStatistic(Statistic arg0, EntityType arg1)
            throws IllegalArgumentException
    {
        caller.incrementStatistic(arg0, arg1);
    }

    @Override
    public void incrementStatistic(Statistic arg0, EntityType arg1, int arg2)
            throws IllegalArgumentException
    {
        caller.incrementStatistic(arg0, arg1, arg2);
    }

    @Override
    public void removeAchievement(Achievement arg0)
    {
        caller.removeAchievement(arg0);
    }

    @Override
    public void sendSignChange(Location arg0, String[] arg1)
            throws IllegalArgumentException
    {
        caller.sendSignChange(arg0, arg1);
    }

    @Override
    public void setResourcePack(String arg0)
    {
        caller.setResourcePack(arg0);
    }

    @Override
    public void setStatistic(Statistic arg0, int arg1)
            throws IllegalArgumentException
    {
        caller.setStatistic(arg0, arg1);
    }

    @Override
    public void setStatistic(Statistic arg0, Material arg1, int arg2)
            throws IllegalArgumentException
    {
        caller.setStatistic(arg0, arg1, arg2);
    }

    @Override
    public void setStatistic(Statistic arg0, EntityType arg1, int arg2)
    {
        caller.setStatistic(arg0, arg1, arg2);
    }

    @Override
    public MainHand getMainHand()
    {
        return caller.getMainHand();
    }

    @Override
    public InventoryView openMerchant(Villager arg0, boolean arg1)
    {
        return caller.openMerchant(arg0, arg1);
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(Set<Material> arg0, int arg1)
    {
        return caller.getLastTwoTargetBlocks(arg0, arg1);
    }

    @Override
    public List<Block> getLineOfSight(Set<Material> arg0, int arg1)
    {
        return caller.getLineOfSight(arg0, arg1);
    }

    @Override
    public Block getTargetBlock(Set<Material> arg0, int arg1)
    {
        return caller.getTargetBlock(arg0, arg1);
    }

    @Override
    public boolean isGliding()
    {
        return caller.isGliding();
    }

    @Override
    public void setGliding(boolean arg0)
    {
        caller.setGliding(arg0);
    }

    @Override
    public AttributeInstance getAttribute(Attribute arg0)
    {
        return caller.getAttribute(arg0);
    }

    @Override
    public boolean isGlowing()
    {
        return caller.isGlowing();
    }

    @Override
    public void setGlowing(boolean arg0)
    {
        caller.setGlowing(arg0);
    }

    @Override
    public Entity getSpectatorTarget()
    {
        return caller.getSpectatorTarget();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void resetTitle()
    {
        caller.resetTitle();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void sendTitle(String arg0, String arg1)
    {
        caller.sendTitle(arg0, arg1);
    }

    @Override
    public void setSpectatorTarget(Entity arg0)
    {
        caller.setSpectatorTarget(arg0);
    }

    @Override
    public void spawnParticle(Particle arg0, Location arg1, int arg2)
    {
        caller.spawnParticle(arg0, arg1, arg2);
    }

    @Override
    public <T> void spawnParticle(Particle arg0, Location arg1, int arg2, T arg3)
    {
        caller.spawnParticle(arg0, arg1, arg2, arg3);
    }

    @Override
    public void spawnParticle(Particle arg0, double arg1, double arg2,
            double arg3, int arg4)
    {
        caller.spawnParticle(arg0, arg1, arg2, arg3, arg4);
    }

    @Override
    public <T> void spawnParticle(Particle arg0, double arg1, double arg2,
            double arg3, int arg4, T arg5)
    {
        caller.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5);
    }

    @Override
    public void spawnParticle(Particle arg0, Location arg1, int arg2,
            double arg3, double arg4, double arg5)
    {
        caller.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5);
    }

    @Override
    public <T> void spawnParticle(Particle arg0, Location arg1, int arg2,
            double arg3, double arg4, double arg5, T arg6)
    {
        caller.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
    }

    @Override
    public void spawnParticle(Particle arg0, Location arg1, int arg2,
            double arg3, double arg4, double arg5, double arg6)
    {
        caller.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
    }

    @Override
    public void spawnParticle(Particle arg0, double arg1, double arg2,
            double arg3, int arg4, double arg5, double arg6, double arg7)
    {
        caller.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
    }

    @Override
    public <T> void spawnParticle(Particle arg0, Location arg1, int arg2,
            double arg3, double arg4, double arg5, double arg6, T arg7)
    {
        caller.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
    }

    @Override
    public <T> void spawnParticle(Particle arg0, double arg1, double arg2,
            double arg3, int arg4, double arg5, double arg6, double arg7, T arg8)
    {
        caller.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
    }

    @Override
    public void spawnParticle(Particle arg0, double arg1, double arg2,
            double arg3, int arg4, double arg5, double arg6, double arg7,
            double arg8)
    {
        caller.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
    }

    @Override
    public <T> void spawnParticle(Particle arg0, double arg1, double arg2,
            double arg3, int arg4, double arg5, double arg6, double arg7,
            double arg8, T arg9)
    {
        caller.spawnParticle(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
    }

	@Override
	public boolean isHandRaised() {

		return caller.isHandRaised();
	}

	@Override
	public PotionEffect getPotionEffect(PotionEffectType arg0) {

		return caller.getPotionEffect(arg0);
	}

	@Override
	public boolean hasAI() {

		return caller.hasAI();
	}

	@Override
	public boolean isCollidable() {

		return caller.isCollidable();
	}

	@Override
	public void setAI(boolean arg0) {

		caller.setAI(arg0);
	}

	@Override
	public void setCollidable(boolean arg0) {

		caller.setCollidable(arg0);
	}

	@Override
	public boolean addScoreboardTag(String arg0) {

		return caller.addScoreboardTag(arg0);
	}

	@Override
	public int getPortalCooldown() {

		return caller.getPortalCooldown();
	}

	@Override
	public Set<String> getScoreboardTags() {

		return caller.getScoreboardTags();
	}

	@Override
	public boolean hasGravity() {

		return caller.hasGravity();
	}

	@Override
	public boolean isInvulnerable() {

		return caller.isInvulnerable();
	}

	@Override
	public boolean isSilent() {

		return caller.isSilent();
	}

	@Override
	public boolean removeScoreboardTag(String arg0) {

		return caller.removeScoreboardTag(arg0);
	}

	@Override
	public void setGravity(boolean arg0) {

		caller.setGravity(arg0);
	}

	@Override
	public void setInvulnerable(boolean arg0) {

		caller.setInvulnerable(arg0);
	}

	@Override
	public void setPortalCooldown(int arg0) {

		caller.setPortalCooldown(arg0);
	}

	@Override
	public void setSilent(boolean arg0) {

		caller.setSilent(arg0);
	}
/*
	@Override
	public Spigot spigot() {

		return caller.spigot();
	}*/

	@Override
	public void stopSound(Sound arg0) {

		caller.stopSound(arg0);
	}

	@Override
	public void stopSound(String arg0) {

		caller.stopSound(arg0);
	}

	@Override
	public InventoryView openMerchant(Merchant arg0, boolean arg1) {

		return caller.openMerchant(arg0, arg1);
	}

	@Override
	public void playSound(Location arg0, Sound arg1, SoundCategory arg2,
			float arg3, float arg4) {

		caller.playSound(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void playSound(Location arg0, String arg1, SoundCategory arg2,
			float arg3, float arg4) {

		caller.playSound(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void stopSound(Sound arg0, SoundCategory arg1) {

		caller.stopSound(arg0, arg1);
	}

	@Override
	public void stopSound(String arg0, SoundCategory arg1) {

		caller.stopSound(arg0, arg1);
	}

	@Override
	public boolean addPassenger(Entity arg0) {

		return caller.addPassenger(arg0);
	}

	@Override
	public List<Entity> getPassengers() {

		return caller.getPassengers();
	}

	@Override
	public boolean removePassenger(Entity arg0) {

		return caller.removePassenger(arg0);
	}

	@Override
	public void sendTitle(String arg0, String arg1, int arg2, int arg3, int arg4) {

		caller.sendTitle(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Spigot spigot() {

		return caller.spigot();
	}
}
