package com.hisense.hiask.robot.multi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hisense.hiask.entity.robot.RobotRouteWayBean;
import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.robot.IRobotBeanDateTime;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.hitools.utils.DateUtils;
import com.hisense.hitools.utils.EmptyUtils;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.MultiTypeAdapter;

import static android.view.View.TEXT_ALIGNMENT_VIEW_START;

/**
 * Created by liudunjian on 2018/10/29.
 */

public class RobotRouteWayViewProvider extends BaseViewProvider<RobotRouteWayBean, RobotRouteWayViewProvider.ViewHolder> {

    private Context context;
    private IRouteWayNavListener routeWayNavListener;

    public RobotRouteWayViewProvider(Context context, IRouteWayNavListener listener) {
        this.context = context;
        this.routeWayNavListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_robot_route_way_item_layout, parent, false), getAdapter(), this.routeWayNavListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RobotRouteWayBean item) {
        holder.bindData(context, item);
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.robot_message_text_time)
        TextView robotMessageTextTime;
        @BindView(R.id.robot_message_text_header_l)
        ImageView robotMessageTextHeaderL;
        @BindView(R.id.robot_message_text_send_error)
        ImageView robotMessageTextSendError;
        @BindView(R.id.robot_message_text_send_loading)
        ProgressBar robotMessageTextSendLoading;
        @BindView(R.id.robot_message_text_content)
        TextView robotMessageTextContent;
        @BindView(R.id.robot_message_text_content_item_layout)
        LinearLayout robotMessageTextContentItemLayout;
        @BindView(R.id.robot_message_more_action)
        TextView robotMessageMoreAction;
        @BindView(R.id.robot_message_text_content_layout)
        LinearLayout robotMessageTextContentLayout;
        @BindView(R.id.robot_message_text_header_r)
        ImageView robotMessageTextHeaderR;

        private MultiTypeAdapter adapter;
        private IRouteWayNavListener listener;
        private RobotRouteWayBean item;

        public ViewHolder(View itemView, MultiTypeAdapter adapter, IRouteWayNavListener listener) {
            super(itemView);
            this.adapter = adapter;
            this.listener = listener;
        }

        public void bindData(Context context, RobotRouteWayBean item) {
            if (EmptyUtils.isEmpty(item))
                return;

            this.item = item;

            if (getAdapterPosition() == 0 || item.getTime() -
                    ((IRobotBeanDateTime) adapter.getItems().get(getAdapterPosition())).getTime()
                    > 30000L) {
                robotMessageTextTime.setVisibility(View.VISIBLE);
                robotMessageTextTime.setText(DateUtils.format(item.getTime(), "HH:mm:ss"));
            } else {
                robotMessageTextTime.setVisibility(View.GONE);
            }

            robotMessageTextSendLoading.setVisibility(View.GONE);

            robotMessageTextHeaderL.setVisibility(View.VISIBLE);
            robotMessageTextHeaderR.setVisibility(View.INVISIBLE);
            robotMessageTextSendError.setVisibility(View.GONE);
            robotMessageTextSendLoading.setVisibility(View.GONE);
            robotMessageTextContent.setBackgroundResource(R.drawable.robot_message_bubble_left);
            robotMessageTextContent.setTextAlignment(TEXT_ALIGNMENT_VIEW_START);

            robotMessageTextContent.setText(String.format(context.getResources().getString(R.string.robot_route_way_multi_item),
                    item.getPayload().getOrigin().getTitle()
                    , item.getPayload().getDestination().getTitle()));


//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) robotMessageTextContentItemLayout.getLayoutParams();
//            layoutParams.gravity = item.getDirection() == 0 ? Gravity.RIGHT : Gravity.LEFT;
//            robotMessageTextContentItemLayout.setLayoutParams(layoutParams);

//            holder = new RobotRichMsgHolder(item.getContent());
//            if (holder.isNeededSplit() && item.getDirection() == 1) {
//                holder.splitContentToParts();
//                robotMessageMoreAction.setVisibility(View.VISIBLE);
//                robotMessageTextContent.setText(holder.nextContent());
//            } else {
//                robotMessageMoreAction.setVisibility(View.GONE);
//                robotMessageTextContent.setText(item.getContent());
//            }
        }

        @OnClick(R.id.robot_message_more_action)
        public void onViewClicked() {
            Log.d("hello","onViewClicked");
            if(this.listener!=null)
                listener.onRouteWayItemClicked(item);
        }
    }

    public interface IRouteWayNavListener {
        void onRouteWayItemClicked(RobotRouteWayBean item);
    }
}
