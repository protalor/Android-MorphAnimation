package com.anim.morph;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.HashMap;
import java.util.Map;

public class MorphAnimation extends Animation implements ViewTreeObserver.OnPreDrawListener {

	/**
	 * Width and height change mode
	 */
	public static final int ANIMATION_MODE = 0;
	/**
	 * Width change mode
	 */
	public static final int ANIMATION_MODE_WIDTH = 1;
	/**
	 * Height change mode
	 */
	public static final int ANIMATION_MODE_HEIGHT = 2;
	private static final String TAG = "MorphAnimation";

	/**
	 * No animation is displayed
	 */
	private static final int ANIMATION_STEP_STOP = 0;

	/**
	 * Animation readiness by detecting size change
	 */
	private static final int ANIMATION_STEP_READY = 1;

	/**
	 * Animation in progress
	 */
	private static final int ANIMATION_STEP_PLAY = 2;

	/**
	 * View change detection standby, if animation is not detected within 1 frame, release animation.
	 */
	private static final int ANIMATION_PRE_DRAW_WAIT_MAX_COUNT = 0;

	/**
	 * Animation default duration
	 */
	private static final int ANIMATION_DEFAULT_DURATION = 300;
	private static Map<View, MorphAnimation> mMorphMap = new HashMap<>();
	View mTargetView;
	int startX;
	int startY;
	int toX;
	int toY;
	private int mAnimationStep = ANIMATION_STEP_STOP;
	private int mOriginalLayoutWidth;
	private int mOriginalLayoutHeight;
	private int mOriginalVisibility;
	private float mWidth;
	private float mHeight;
	private float mEndWidth;
	private float mEndHeight;
	private int mPreDrawWaitCount = 0;
	private int mAnimationMode = 0;
	private int mAnimationDuration = ANIMATION_DEFAULT_DURATION;
	private MorphListener mMorphListener;

	private MorphAnimation(View targetView) {
		super();
		this.mTargetView = targetView;
		startX = targetView.getWidth();
		startY = targetView.getHeight();
	}

	/**
	 * Applied height variation
	 * Check the following call conditions
	 * 1. Call it after type conversion only if the change in the shape of the view is obvious.
	 * 2. It should be the parent's existing View.
	 * 3. Recommended for code corresponding to user action.
	 * 4. Do not change the shape of the target view's animation in external code. However, it is possible to call the morph () method continuously.
	 *
	 * @param targetView	target view
	 */
	public static MorphAnimation morph(final View targetView) {
		return morph(targetView, ANIMATION_MODE_HEIGHT, ANIMATION_DEFAULT_DURATION);
	}

	/**
	 * Applied height variation
	 * Check the following call conditions
	 * 1. Call it after type conversion only if the change in the shape of the view is obvious.
	 * 2. It should be the parent's existing View.
	 * 3. Recommended for code corresponding to user action.
	 * 4. Do not change the shape of the target view's animation in external code. However, it is possible to call the morph () method continuously.
	 *
	 * @param targetView	target view
	 * @param mode
	 *            input static define value {@link MorphAnimation#ANIMATION_MODE_WIDTH},
	 *            {@link MorphAnimation#ANIMATION_MODE_HEIGHT}.
	 */
	public static MorphAnimation morph(final View targetView, int mode) {
		return morph(targetView, mode, ANIMATION_DEFAULT_DURATION);
	}

	/**
	 * Applied height variation
	 * Check the following call conditions
	 * 1. Call it after type conversion only if the change in the shape of the view is obvious.
	 * 2. It should be the parent's existing View.
	 * 3. Recommended for code corresponding to user action.
	 * 4. Do not change the shape of the target view's animation in external code. However, it is possible to call the morph () method continuously.
	 *
	 * @param targetView	target view
	 * @param mode
	 *            input static define value {@link MorphAnimation#ANIMATION_MODE_WIDTH},
	 *            {@link MorphAnimation#ANIMATION_MODE_HEIGHT}.
	 * @param duration
	 *            Animation duration
	 */
	public static MorphAnimation morph(final View targetView, int mode, int duration) {

		if (targetView != null && targetView.getParent() != null) {
			if (targetView.getAnimation() != null)
				targetView.getAnimation().cancel();

			if (mMorphMap.containsKey(targetView)) {
				MorphAnimation morphAnimation = mMorphMap.get(targetView);
				morphAnimation.mAnimationStep = ANIMATION_STEP_STOP;
				morphAnimation.mTargetView.getViewTreeObserver().removeOnPreDrawListener(morphAnimation);
				mMorphMap.remove(targetView);
			}

			final MorphAnimation morphView = new MorphAnimation(targetView);

			mMorphMap.put(targetView, morphView);

			morphView.mTargetView = targetView;

			morphView.mOriginalVisibility = morphView.mTargetView.getVisibility();

			morphView.mTargetView.setVisibility(View.VISIBLE);

			morphView.mAnimationMode = mode;

			if (duration > 0) {
				morphView.mAnimationDuration = duration;
			}

			LayoutParams params = morphView.mTargetView.getLayoutParams();
			morphView.mOriginalLayoutWidth = params.width;
			morphView.mOriginalLayoutHeight = params.height;

			morphView.mWidth = morphView.mEndWidth = morphView.mTargetView.getMeasuredWidth();
			morphView.mHeight = morphView.mEndHeight = morphView.mTargetView.getMeasuredHeight();
			morphView.mTargetView.getViewTreeObserver().addOnPreDrawListener(morphView);

			return morphView;
		} else {
			return null;
		}
	}

	public void setOnMorphListener(MorphListener listener) {
		mMorphListener = listener;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		try {
			if (mTargetView != null && mAnimationStep == ANIMATION_STEP_PLAY) {
				LayoutParams l = mTargetView.getLayoutParams();

				if (mAnimationMode == ANIMATION_MODE_WIDTH || mAnimationMode == ANIMATION_MODE)
					l.width = (int) (startX + (toX - startX) * interpolatedTime);

				if (mAnimationMode == ANIMATION_MODE_HEIGHT || mAnimationMode == ANIMATION_MODE)
					l.height = (int) (startY + (toY - startY) * interpolatedTime);

				mTargetView.requestLayout();
			}
		} catch (Exception e) {
			Log.e(TAG, "Exception applyTransformation", e);
		}
	}

	@Override
	public boolean willChangeBounds() {
		return true;
	}

	@Override
	public boolean onPreDraw() {

		try {

			boolean rtn = true;

			switch (mAnimationStep) {
				case ANIMATION_STEP_READY:
					rtn = false;
					break;

				case ANIMATION_STEP_PLAY:
					if ((int) mEndWidth == mTargetView.getMeasuredWidth()
							&& (int) mEndHeight == mTargetView.getMeasuredHeight())
						rtn = false;
					break;

				case ANIMATION_STEP_STOP:
					int measuredWidth = mOriginalVisibility == View.GONE ? 0 : mTargetView.getMeasuredWidth();
					int measuredHeight = mOriginalVisibility == View.GONE ? 0 : mTargetView.getMeasuredHeight();

					if ((int) mWidth != measuredWidth
							&& (mAnimationMode == ANIMATION_MODE_WIDTH || mAnimationMode == ANIMATION_MODE)) {
						mEndWidth = measuredWidth;
						rtn = false;
					}
					if ((int) mHeight != measuredHeight
							&& (mAnimationMode == ANIMATION_MODE_HEIGHT || mAnimationMode == ANIMATION_MODE)) {
						mEndHeight = measuredHeight;
						rtn = false;
					}

					if (rtn) {
						mPreDrawWaitCount++;

						if (mPreDrawWaitCount > ANIMATION_PRE_DRAW_WAIT_MAX_COUNT) {
							finishAnimation();
						}
					} else {

						mAnimationStep = ANIMATION_STEP_PLAY;
						mTargetView.getViewTreeObserver().removeOnPreDrawListener(MorphAnimation.this);

						LayoutParams l = mTargetView.getLayoutParams();

						if (mAnimationMode == ANIMATION_MODE_WIDTH || mAnimationMode == ANIMATION_MODE)
							l.width = (int) ((int) mWidth == 0 ? 1 : mWidth);

						if (mAnimationMode == ANIMATION_MODE_HEIGHT || mAnimationMode == ANIMATION_MODE)
							l.height = (int) ((int) mHeight == 0 ? 1 : mHeight);

						mTargetView.requestLayout();

						this.toX = (int) mEndWidth;
						this.toY = (int) mEndHeight;

						setAnimationListener(new AnimationListener() {

							@Override
							public void onAnimationStart(Animation animation) {
								mAnimationStep = ANIMATION_STEP_PLAY;
							}

							@Override
							public void onAnimationEnd(Animation animation) {
								try {
									if (mAnimationStep != ANIMATION_STEP_STOP) {
										finishAnimation();
									}
								} catch (Exception e) {
									Log.e(TAG, "Exception onAnimationEnd", e);
								}
							}

							@Override
							public void onAnimationRepeat(Animation animation) {
								// do nothing
							}
						});

						setInterpolator(new AccelerateDecelerateInterpolator());
						setDuration(mAnimationDuration);
						mTargetView.startAnimation(MorphAnimation.this);
					}

					break;
				default:
					break;
			}

			return rtn; // true : refresh
		} catch (Exception e) {
			Log.e(TAG, "Exception onPreDraw", e);
			finishAnimation();
			return true; // force finish animation
		}
	}

	private void finishAnimation() {
		mAnimationStep = ANIMATION_STEP_STOP;

		if (mTargetView != null && mTargetView.getViewTreeObserver() != null)
			mTargetView.getViewTreeObserver().removeOnPreDrawListener(MorphAnimation.this);

		if (mMorphMap.size() > 0)
			mMorphMap.remove(mTargetView);

		restoreOriginalLayout();

		if (mTargetView != null)
			mTargetView.requestLayout();

		if (mMorphListener != null) {
			mMorphListener.onMorphComplete();
			mMorphListener = null;
		}
	}

	private void restoreOriginalLayout() {

		if (mTargetView != null) {
			LayoutParams params = mTargetView.getLayoutParams();

			if (params != null) {
				params.width = mOriginalLayoutWidth;
				params.height = mOriginalLayoutHeight;

				mTargetView.setVisibility(mOriginalVisibility);
			}

		}
	}

	public interface MorphListener {
		public void onMorphComplete();
	}

}
