package org.droidparts.gram.adapter;

import static org.droidparts.util.Strings.join;

import org.droidparts.adapter.cursor.EntityCursorAdapter;
import org.droidparts.adapter.tag.IconText2Tag;
import org.droidparts.gram.R;
import org.droidparts.gram.model.Image;
import org.droidparts.gram.persist.ImageEntityManager;
import org.droidparts.util.ImageAttacher;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageListAdapter extends EntityCursorAdapter<Image> {

	private final ImageAttacher imageAttacher;

	public ImageListAdapter(Activity activity) {
		super(activity, new ImageEntityManager(activity));
		imageAttacher = new ImageAttacher(activity);
	}

	@Override
	public final View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = layoutInflater.inflate(R.layout.list_row_image, null);
		IconText2Tag tag = new IconText2Tag(view);
		view.setTag(tag);
		return view;
	}

	@Override
	public void bindView(Context context, View view, Image item) {
		entityManager.fillForeignKeys(item);
		IconText2Tag tag = (IconText2Tag) view.getTag();
		tag.text1.setText(item.captionText);
		tag.text2.setText(buildDescription(item));
		// XXX
		ImageView placeholderView = (ImageView) view
				.findViewById(R.id.view_icon_placeholder);
		imageAttacher.attachImageCrossFaded(placeholderView, tag.icon,
				item.thumbnailUrl);
	}

	private Spanned buildDescription(Image img) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b>").append(img.filter.name).append("</b>");
		sb.append(" ");
		sb.append(join(img.tags, ", ", null));
		return Html.fromHtml(sb.toString());
	}

}
