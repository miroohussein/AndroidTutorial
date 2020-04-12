package cat.cattutorial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapterNew extends ListAdapter<Contact,ContactAdapterNew.ContactViewHolder> {



    private OnContactClicked onItemClickedListener;


    /*static DiffUtil.ItemCallback<Contact> diffCallback = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getNumber().equals(newItem.getNumber());

        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    };*/
     ContactAdapterNew(DiffUtil.ItemCallback<Contact> diffCallback, OnContactClicked onContactClicked ) {
        super(diffCallback);
        this.onItemClickedListener= onContactClicked;
    }

    interface OnContactClicked {
        void c(Contact contact, int position);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_new, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, final int position) {
        holder.onBind(getItem(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickedListener.c(getItem(position), position);
            }
        });
    }


    class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvNumber;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.tv_number);
        }

        void onBind(Contact contact) {
            tvName.setText(contact.getName());
            tvNumber.setText(contact.getNumber());
        }
    }
}
