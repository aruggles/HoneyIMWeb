package honeypot.actions;

import honeypot.models.Status;
import honeypot.services.WepawetService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class Index extends ActionSupport {
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 3406722313955012543L;
	/**
	 * The Wepawet Service.
	 */
	private WepawetService wepawetService;
	/**
	 * The wepawet status object.
	 */
	private Status wepawetStatus;

	/**
	 * Retrieves the service status.
	 * {@inheritDoc}
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
    public String execute() {
    	wepawetStatus = wepawetService.getStatus();
        return Action.SUCCESS;
    }

	/**
	 * Returns wepawetStatus.
	 * @return the wepawetStatus.
	 */
	public Status getWepawetStatus() {
		return wepawetStatus;
	}

	/**
	 * Sets wepawetService.
	 * @param wepawetService the wepawetService to set.
	 */
	public void setWepawetService(WepawetService wepawetService) {
		this.wepawetService = wepawetService;
	}

}
