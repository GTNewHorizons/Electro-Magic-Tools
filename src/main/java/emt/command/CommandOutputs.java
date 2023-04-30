package emt.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import emt.util.EMTEssentiasOutputs;
import emt.util.EMTTextHelper;
import thaumcraft.api.aspects.Aspect;

public class CommandOutputs extends CommandBase {

    public static ItemStack mkbook() {
        String text = EMTTextHelper.localize("gui.EMT.book.aspect.output.essentia.eu") + ": \n";
        String text2 = EMTTextHelper.localize("gui.EMT.book.aspect.output.essentia.eu") + " /t: \n";
        String text3 = EMTTextHelper.localize("gui.EMT.book.aspect.output.essentia.eu") + " /t singleblock: \n";

        final String title = EMTTextHelper.localize("gui.EMT.book.aspect.output.eu.title");

        final ArrayList<String> singleblock = new ArrayList<String>();
        final ArrayList<String> bookL = new ArrayList<String>();
        final ArrayList<String> bookL2 = new ArrayList<String>();

        byte counter = 0;

        for (final Aspect aspect : Aspect.aspects.values()) {
            ++counter;

            if (aspect.equals(Aspect.ENERGY) || aspect.equals(Aspect.TREE)
                    || aspect.equals(Aspect.FIRE)
                    || aspect.equals(Aspect.AURA)
                    || aspect.equals(Aspect.GREED)
                    || aspect.equals(Aspect.AIR))
                text3 += aspect.getTag() + ": "
                        + String.valueOf(EMTEssentiasOutputs.outputs.get(aspect.getTag()) / 20D / 20D)
                        + "EU\n";

            if (counter < 12) {
                text2 += aspect.getTag() + ": "
                        + String.valueOf(EMTEssentiasOutputs.outputs.get(aspect.getTag()) / 20D)
                        + "EU/t\n";
                text += aspect.getTag() + ": "
                        + String.valueOf(EMTEssentiasOutputs.outputs.get(aspect.getTag()))
                        + "EU\n";
            } else {
                text2 += aspect.getTag() + ": "
                        + String.valueOf(EMTEssentiasOutputs.outputs.get(aspect.getTag()) / 20D)
                        + "EU\n";
                text += aspect.getTag() + ": "
                        + String.valueOf(EMTEssentiasOutputs.outputs.get(aspect.getTag()))
                        + "EU\n";
                bookL.add(text);
                bookL2.add(text2);
                text2 = "";
                text = "";
                counter = 0;
            }
        }

        singleblock.add(text3);

        if (counter != 0) {
            bookL.add(text);
            bookL2.add(text2);
        }

        final ItemStack book = new ItemStack(Items.written_book);
        book.setTagInfo("author", new NBTTagString("Tombenpotter, bartimaeusnek"));
        book.setTagInfo("title", new NBTTagString(title));
        final NBTTagCompound nbttagcompound = book.getTagCompound();
        final NBTTagList bookPages = new NBTTagList();

        for (int i = 0; i < singleblock.size(); i++) {
            bookPages.appendTag(new NBTTagString(singleblock.get(i)));
        }
        for (int i = 0; i < bookL.size(); i++) {
            bookPages.appendTag(new NBTTagString(bookL.get(i)));
        }
        for (int i = 0; i < bookL2.size(); i++) {
            bookPages.appendTag(new NBTTagString(bookL2.get(i)));
        }
        nbttagcompound.setTag("pages", bookPages);
        return book;
    }

    @Override
    public String getCommandName() {
        return "emt_outputs";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/emt_outputs";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender command, String[] stringArray) {
        return null;
    }

    @Override
    public int compareTo(Object object) {
        if (object instanceof ICommand) {
            return this.compareTo((ICommand) object);
        } else {
            return 0;
        }
    }

    @Override
    public void processCommand(ICommandSender command, String[] astring) {
        ItemStack book = mkbook();

        if (!command.getEntityWorld().getPlayerEntityByName(command.getCommandSenderName()).inventory
                .addItemStackToInventory(book))
            command.getEntityWorld().getPlayerEntityByName(command.getCommandSenderName()).entityDropItem(book, 0);
    }
}
