package wei.mark.qri;

import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;
import wei.mark.standout.ui.Window;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class SimpleWindow extends StandOutWindow {

	public int firstX, firstY, lastX, lastY;

	@Override
	public String getAppName() {
		return "QRI";
	}

	@Override
	public int getAppIcon() {
		return android.R.drawable.ic_menu_close_clear_cancel;
	}

	@Override
	public void createAndAttachView(int id, FrameLayout frame) {
		// create a new layout from body.xml
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		//inflater.inflate(R.layout.simple, frame, true);
		View view = inflater.inflate(R.layout.simple, frame, true);

	}

	// the window will be centered
	@Override
	public StandOutLayoutParams getParams(int id, Window window) {
		return new StandOutLayoutParams(id, 89, 90,
				StandOutLayoutParams.CENTER, StandOutLayoutParams.CENTER);
	}

	// move the window by dragging the view
	@Override
	public int getFlags(int id) {
		return super.getFlags(id) | StandOutFlags.FLAG_BODY_MOVE_ENABLE
				| StandOutFlags.FLAG_WINDOW_FOCUSABLE_DISABLE
				| StandOutFlags.FLAG_WINDOW_EDGE_LIMITS_ENABLE



				;
	}

	@Override
	public String getPersistentNotificationMessage(int id) {
		return "Click to close the QRI";
	}

	@Override
	public boolean onTouchBody(int id, Window window, View view,
							   MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				firstX = (int) event.getRawX();
				firstY = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_UP:
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();

				boolean tap = Math.abs(firstX - lastX ) < 10
						&& Math.abs(firstY -lastY) < 10;

				if (tap){



					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
					intent.setPackage("com.google.zxing.client.android");
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

					Context context = getApplicationContext();
					context.startActivity(intent);
					return true;
				}



				break;
		}

		return false;
	}

	@Override
	public Intent getPersistentNotificationIntent(int id) {
		return StandOutWindow.getCloseIntent(this, SimpleWindow.class, id);
	}
}
