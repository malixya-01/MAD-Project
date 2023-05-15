package com.example.supportapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.DataClasses.reqFromDonorData
import com.example.supportapp.R

class viewAllDonorsReqAdapter(var c: Context, var mList: List<reqFromDonorData>) :
    RecyclerView.Adapter<viewAllDonorsReqAdapter.ViewAllDonorsReqViewHolder>() {

    private var popMenulistner : popupMenuOnItemClickInterface? = null
    fun setPopMenulistner(popMenulistner: popupMenuOnItemClickInterface){
        this.popMenulistner = popMenulistner
    }

    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class ViewAllDonorsReqViewHolder(itemView: View, listner: onItemClickListner) :RecyclerView.ViewHolder(itemView) {
        val tvUserName : TextView = itemView.findViewById(R.id.tvName)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)
        val tvPhone : TextView = itemView.findViewById(R.id.tvContactNumber)
        val tvEmail : TextView = itemView.findViewById(R.id.tvEmail)
        val tvDescription : TextView = itemView.findViewById(R.id.tvDescription)
        val userDp : ImageView = itemView.findViewById(R.id.ivViewDp)
        val moreBtn : ImageView = itemView.findViewById(R.id.btnMore)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }

            moreBtn.setOnClickListener { popupMenus(it) }   //set onclick listeners on menu items
        }

        private fun popupMenus(v : View) {
            val position = mList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.popup_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.popup_edit -> {
                        popMenulistner?.onEditBtnClicked(position)
                        true
                    }
                    R.id.popup_delete->{
                        popMenulistner?.onDeleteBtnClicked(position)
                        true
                    }
                    else-> true
                }
            }
            popupMenus.show()

        }

    }
    interface popupMenuOnItemClickInterface{
        fun onEditBtnClicked(supFrData: reqFromDonorData)
        fun onDeleteBtnClicked(supFrData: reqFromDonorData)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllDonorsReqViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_view_sup_msgs, parent, false)
        return ViewAllDonorsReqViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewAllDonorsReqViewHolder, position: Int) {
        //retrieve user name and profile picture and bind to view holder
        //getUserDetails(position, holder)

        //display empty values as N/A
        if( !mList[position].phoneNumber.isNullOrBlank()){
            holder.tvPhone.text = mList[position].phoneNumber
        } else{
            holder.tvPhone.text = "N/A"
        }

        if( !mList[position].email.isNullOrBlank()){
            holder.tvEmail.text = mList[position].email
        } else{
            holder.tvEmail.text = "N/A"
        }

        holder.tvUserName.text = mList[position].supporterId
        holder.tvDate.text = mList[position].date
        holder.tvDescription.text = mList[position].message

    }

   /* private fun getUserDetails(position : Int, holder: ViewAllDonorsReqViewHolder){
        var userId = mList[position].supporterId!!

        var databaseReference = FirebaseDatabase.getInstance().reference.child("users")
        databaseReference.child(userId).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                var user = snapshot.getValue(User::class.java)!!

                //set holder tvName
                holder.tvUserName.text = user.name

                //retrieve user profile picture
                getUserProfilePicture(userId, holder)

            }
            override fun onCancelled(error: DatabaseError) {
                // Toast.makeText(activity, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
            }


        })

    }

    private fun getUserProfilePicture(uid: String, holder: ViewAllDonorsReqViewHolder){
        //find image named with the current uid
        var storageReference = FirebaseStorage.getInstance().reference.child("Users/$uid")

        //create temporary local file to store the retrieved image
        val localFile = File.createTempFile("tempImage", ".jpg")

        //retrieve image and store it to created temp file
        storageReference.getFile(localFile).addOnSuccessListener {

            //covert temp file to bitmap
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)

            //bind image
            holder.userDp.setImageBitmap(bitmap)
        }.addOnFailureListener{

        }
    }*/

}