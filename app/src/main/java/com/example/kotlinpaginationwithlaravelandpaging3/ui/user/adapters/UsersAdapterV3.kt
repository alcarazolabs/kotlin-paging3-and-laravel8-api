package com.example.kotlinpaginationwithlaravelandpaging3.ui.user.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpaginationwithlaravelandpaging3.data.model.UserData
import com.example.kotlinpaginationwithlaravelandpaging3.databinding.UserItemBinding

class UsersAdapterV3(private val userClick: UserClickListener):
    PagingDataAdapter<UserData, UsersAdapterV3.MyViewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: UsersAdapterV3.MyViewHolder, position: Int) {
        // holder.bind(getItem(position)!!) Codigo original. Ocasiona un crash

        //modificado para evitar crash cuando se llega al final y al regresar al top ya no se crashea
        //getItem(position)?.let { holder.bind(it) }
        //con onclick:
        getItem(position)?.let { holder.bind(it, userClick.clickListener) }
    }
/*
  Sin binding:

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapterV3.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return MyViewHolder(inflater)
    }
 */
    // Con binding:
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            //forma normal:
            return MyViewHolder(
                UserItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

        }
/*
 - Sin binding:
  - El nombre de la clase MyViewHolder pudo haberse llamado UserViewHolder o UserMainViewHolder..
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val tvName: TextView = view.findViewById(R.id.name)
        val tvEmail: TextView = view.findViewById(R.id.email)
        val userId: TextView = view.findViewById(R.id.userId)

        fun bind(data: UserData) {
            tvName.text = data.name
            tvEmail.text = data.email
            userId.text = "#${data.id}"

        }
    }
  */

    //Con binding:
    class MyViewHolder(val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {
        //Attach the click listener on the current view in the ViewHolder
        //Nota: con el fin de aplicar el onClick al metodo bind se le pasa otro parametro llamado clickListener
        fun bind(data: UserData, clickListener: (UserData)->Unit) {
            binding.name.setOnClickListener {
                clickListener(data)
            }
            binding.name.text = data.name
            binding.email.text = data.email
            binding.userId.text = "#${data.id}"

        }
    }



    class DiffUtilCallBack: DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.name == newItem.name
        }

    }


}

data class UserClickListener(val clickListener: (user: UserData) -> Unit)
