package com.example.supportapp.Adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.DataClasses.User
import com.example.supportapp.DataClasses.supportFundraiserData
import com.example.supportapp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import kotlin.coroutines.coroutineContext

class viewMessagesAdapter(var mList: List<supportFundraiserData>) :
    RecyclerView.Adapter<viewMessagesAdapter.viewMessageViewHolder>() {

    inner class viewMessageViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val tvUserName : TextView = itemView.findViewById(R.id.tvName)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)
        val tvPhone : TextView = itemView.findViewById(R.id.tvContactNumber)
        val tvEmail : TextView = itemView.findViewById(R.id.tvEmail)
        val tvDescription : TextView = itemView.findViewById(R.id.tvDescription)
        val userDp : ImageView = itemView.findViewById(R.id.ivViewDp)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewMessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_view_sup_msgs, parent, false)
        return viewMessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: viewMessageViewHolder, position: Int) {
        //retrieve user name and profile picture and bind to view holder
        /*positiongetUserDetails(position, holder)

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
        }*/

        holder.tvUserName.text = mList[position].supporterId
        holder.tvDate.text = mList[position].date
        holder.tvDescription.text = mList[position].message

    }

    /*private fun getUserDetails(position : Int, holder: viewMessageViewHolder){
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

    }*/

    /*private fun getUserProfilePicture(uid: String, holder: viewMessageViewHolder){
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