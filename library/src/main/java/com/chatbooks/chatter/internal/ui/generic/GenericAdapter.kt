package com.chatbooks.chatter.internal.ui.generic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chatbooks.chatter.R
import com.chatbooks.chatter.internal.data.entity.GenericTuple
import java.text.DateFormat

internal class GenericAdapter(val listener: OnClickListListener) :
        RecyclerView.Adapter<GenericAdapter.GenericViewHolder>() {

    private var data: List<GenericTuple> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view =
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.chatter_list_item_generic, parent, false)
        return GenericViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        val generic = data[position]
        holder.bind(generic)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<GenericTuple>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class GenericViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val subTitleView: TextView = itemView.findViewById(R.id.subtitle)
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val messageView: TextView = itemView.findViewById(R.id.message)
        private val dateView: TextView = itemView.findViewById(R.id.date)
        private var throwableId: Long? = null

        init {
            itemView.setOnClickListener(this)
        }

        internal fun bind(generic: GenericTuple) = with(generic) {
            throwableId = id

            titleView.text = title
            subTitleView.text = subTitle
            messageView.text = message
            dateView.text = formattedDate
        }

        override fun onClick(v: View) {
            throwableId?.let {
                listener.onClick(it, adapterPosition)
            }
        }
    }

    private val GenericTuple.formattedDate: String
        get() {
            return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
                    .format(this.date)
        }

    interface OnClickListListener {
        fun onClick(throwableId: Long, position: Int)
    }
}
