package dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 *
 * @author SG
 * @time 2018/5/12

 **/
public abstract class SGBaseAdapter<T> extends RecyclerView.Adapter<BindViewHolder> {

    private final int layoutRes;
    private List<T> datas;
    private OnAdapterItemClickListener adapterItemClickListener;
    private SGDialog dialog;

    protected abstract void onBind(BindViewHolder holder, int position, T t);

    public SGBaseAdapter(@LayoutRes int layoutRes, List<T> datas) {
        this.layoutRes = layoutRes;
        this.datas = datas;
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(final BindViewHolder holder, final int position) {
        onBind(holder, position, datas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterItemClickListener.onItemClick(holder, position, datas.get(position), dialog);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setTDialog(SGDialog tDialog) {
        this.dialog = tDialog;
    }

    public interface OnAdapterItemClickListener<T> {
        void onItemClick(BindViewHolder holder, int position, T t, SGDialog tDialog);
    }

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        this.adapterItemClickListener = listener;
    }
}
