package me.RabidCrab.Vote;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

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
    public void setItemInHand(ItemStack arg0)
    {
        caller.setItemInHand(arg0);
    }

    @Override
    public void damage(int arg0)
    {
        caller.damage(arg0);
    }

    @Override
    public void damage(int arg0, Entity arg1)
    {
        caller.damage(arg0, arg1);
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
    public int getHealth()
    {
        return caller.getHealth();
    }

    @Override
    public int getLastDamage()
    {
        return caller.getLastDamage();
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> arg0, int arg1)
    {
        return caller.getLastTwoTargetBlocks(arg0, arg1);
    }

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

    @Override
    public Block getTargetBlock(HashSet<Byte> arg0, int arg1)
    {
        return caller.getTargetBlock(arg0, arg1);
    }

    @Override
    public Vehicle getVehicle()
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
    public void setHealth(int arg0)
    {
        caller.setHealth(arg0);
    }

    @Override
    public void setLastDamage(int arg0)
    {
        caller.setLastDamage(arg0);
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
    public Arrow shootArrow()
    {
        return caller.shootArrow();
    }

    @Override
    public Egg throwEgg()
    {
        return caller.throwEgg();
    }

    @Override
    public Snowball throwSnowball()
    {
        return caller.throwSnowball();
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

    @SuppressWarnings("deprecation")
    @Override
    public void teleportTo(Location arg0)
    {
        caller.teleportTo(arg0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void teleportTo(Entity arg0)
    {
        caller.teleportTo(arg0);
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
    public void playEffect(Location arg0, Effect arg1, int arg2)
    {
        caller.playEffect(arg0, arg1, arg2);
    }

    @Override
    public void playNote(Location arg0, byte arg1, byte arg2)
    {
        caller.playNote(arg0, arg1, arg2);
    }

    @Override
    public void saveData()
    {
        
    }

    @Override
    public void sendBlockChange(Location arg0, Material arg1, byte arg2)
    {
        caller.sendBlockChange(arg0, arg1, arg2);
    }

    @Override
    public void sendBlockChange(Location arg0, int arg1, byte arg2)
    {
        caller.sendBlockChange(arg0, arg1, arg2);
    }

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

    @SuppressWarnings("deprecation")
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
    
}
