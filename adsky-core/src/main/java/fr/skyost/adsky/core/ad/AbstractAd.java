package fr.skyost.adsky.core.ad;

import fr.skyost.adsky.core.AdSkyConfiguration;
import xyz.algogo.core.evaluator.ExpressionEvaluator;
import xyz.algogo.core.evaluator.variable.Variable;
import xyz.algogo.core.evaluator.variable.VariableType;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents an AdSky Ad.
 */

public abstract class AbstractAd {

	/**
	 * The Title ad type.
	 */

	public static final int TYPE_TITLE = 0;

	/**
	 * The Chat ad type.
	 */

	public static final int TYPE_CHAT = 1;

	/**
	 * Ad's username.
	 */

	private String username;

	/**
	 * Ad's type.
	 */

	private int type;

	/**
	 * Ad's title.
	 */

	private String title;

	/**
	 * Ad's message.
	 */

	private String message;

	/**
	 * Ad's interval.
	 */

	private int interval;

	/**
	 * Ad's expiration.
	 */

	private long expiration;

	/**
	 * Ad's duration.
	 */

	private int duration;

	/**
	 * Creates a new Ad instance.
	 *
	 * @param username The username.
	 * @param type The type (see Ad constants).
	 * @param title The title.
	 * @param message The message.
	 * @param interval The interval.
	 * @param expiration The expiration.
	 * @param duration The duration.
	 */

	protected AbstractAd(final String username, final int type, final String title, final String message, final int interval, final long expiration, final int duration) {
		this.username = username;
		this.type = type;
		this.title = title;
		this.message = message;
		this.interval = interval;
		this.expiration = expiration;
		this.duration = duration;
	}

	/**
	 * Allows to clone an AbstractAd.
	 *
	 * @param ad The AbstractAd to clone.
	 */

	protected AbstractAd(final AbstractAd ad) {
		this(ad.getUsername(), ad.getType(), ad.getTitle(), ad.getMessage(), ad.getInterval(), ad.getExpiration(), ad.getDuration());
	}

	/**
	 * Gets the username.
	 *
	 * @return The username.
	 */

	public final String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username The username.
	 */

	public final void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * Gets the type (see Ad constants).
	 *
	 * @return The type.
	 */

	public final int getType() {
		return type;
	}

	/**
	 * Checks if this Ad is a Title Ad.
	 *
	 * @return Whether this Ad is a Title Ad.
	 */

	public final boolean isTitleAd() {
		return type == TYPE_TITLE;
	}

	/**
	 * Checks if this Ad is a Chat Ad.
	 *
	 * @return Whether this Ad is a Chat Ad.
	 */

	public final boolean isChatAd() {
		return !isTitleAd();
	}

	/**
	 * Sets the type of this Ad.
	 *
	 * @param type The type.
	 */

	public final void setType(final int type) {
		this.type = type;
	}

	/**
	 * Gets the title.
	 *
	 * @return The title.
	 */

	public final String getTitle() {
		return title;
	}

	/**
	 * Sets the title of this Ad.
	 *
	 * @param title The title.
	 */

	public final void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Gets the message.
	 *
	 * @return The message.
	 */

	public final String getMessage() {
		return message;
	}

	/**
	 * Sets the message of this Ad.
	 *
	 * @param message The message.
	 */

	public final void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * Gets the interval.
	 *
	 * @return The interval.
	 */

	public final int getInterval() {
		return interval;
	}

	/**
	 * Sets the interval of this Ad.
	 *
	 * @param interval The interval.
	 */

	public final void setInterval(final int interval) {
		this.interval = interval;
	}

	/**
	 * Gets the expiration.
	 *
	 * @return The expiration.
	 */

	public final long getExpiration() {
		return expiration;
	}

	/**
	 * Sets the expiration of this Ad.
	 *
	 * @param expiration The expiration.
	 */

	public final void setExpiration(final long expiration) {
		this.expiration = expiration;
	}

	/**
	 * Gets the duration.
	 *
	 * @return The duration.
	 */

	public final int getDuration() {
		return duration;
	}

	public final void setDuration(final int duration) {
		this.duration = duration;
	}

	/**
	 * Broadcasts this ad.
	 */

	public abstract void broadcast();

	/**
	 * Multiplies this ad according to the broadcast interval.
	 *
	 * @return An array containing all ads.
	 */

	public AbstractAd[] multiply() {
		final int interval = this.getInterval();

		AbstractAd[] array = new AbstractAd[interval];
		array[0] = this;
		for(int i = 1; i < interval; i++) {
			array[i] = this.copy();
		}

		return array;
	}

	/**
	 * Copies this ad instance.
	 *
	 * @return A copy of this instance.
	 */

	public abstract AbstractAd copy();

	/**
	 * Evaluates the distribution function at the given hour.
	 *
	 * @param config The AdSky config.
	 * @param preferredHour The preferred hour.
	 * @param hour The hour.
	 * @param adsNumber Today's ads number.
	 *
	 * @return The number of ads to broadcast at the given hour.
	 */

	public static int getAdsPerHour(final AdSkyConfiguration config, final int preferredHour, final int hour, final int adsNumber) {
		final ExpressionEvaluator evaluator = new ExpressionEvaluator();
		evaluator.putVariable(new Variable("h", VariableType.NUMBER, new BigDecimal(preferredHour)));
		evaluator.putVariable(new Variable("x", VariableType.NUMBER, new BigDecimal(hour)));
		evaluator.putVariable(new Variable("n", VariableType.NUMBER, new BigDecimal(adsNumber)));

		final BigDecimal result = (BigDecimal)evaluator.evaluate(config.getAdsDistributionFunction()).getValue();
		return result.setScale(0, RoundingMode.UP).intValue();
	}

}