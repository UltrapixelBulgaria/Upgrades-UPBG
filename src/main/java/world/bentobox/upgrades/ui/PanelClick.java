package world.bentobox.upgrades.ui;

import org.bukkit.event.inventory.ClickType;

import world.bentobox.bentobox.api.panels.Panel;
import world.bentobox.bentobox.api.panels.PanelItem.ClickHandler;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.upgrades.api.UpgradeAPI;

public class PanelClick implements ClickHandler {

	public PanelClick(UpgradeAPI upgrade, Island island) {
		this.upgrade = upgrade;
		this.island = island;
	}
	
	@Override
	@SuppressWarnings("java:S3516")
	public boolean onClick(Panel panel, User user, ClickType clickType, int slot) {
		if (this.upgrade == null) {
			return true;
		}

		if (this.upgrade.getUpgradeValues(user) == null
				&& this.upgrade.getOwnDescription(user) == null) {
			user.sendMessage("upgrades.error.maxlevel");
			return true;
		}

		if (!this.upgrade.canUpgrade(user, this.island)) {
			user.sendMessage("upgrades.error.cannotafford");
			return true;
		}

		user.closeInventory();
		if (this.upgrade.doUpgrade(user, this.island)) {
			user.sendMessage("upgrades.message.upgradesuccess",
					"[name]", this.upgrade.getDisplayName());
		}
		return true;
	}
	
	private UpgradeAPI upgrade;
	private Island island;
	
}
