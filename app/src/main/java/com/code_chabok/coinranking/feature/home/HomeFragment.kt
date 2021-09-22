package com.code_chabok.coinranking.feature.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.databinding.FragmentHomeBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : CoinFragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.button).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)

        }



        /*val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.setTitle("Hello")
        dialog.setMessage("This is Sample")*/

        /*view.findViewById<View>(R.id.button).setOnTouchListener(OnTouchListener { v, event ->

            var x = event.x
            var y = event.y
            v.performClick()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> { // RELEASED
                    dialog.show()
                    //oast.makeText(this.context, "Action Up", Toast.LENGTH_SHORT).show()
                    //Log.i("TAGAA", "onViewCreated: ")
                    Toast.makeText(v.context, "Down", Toast.LENGTH_SHORT).show()
                    *//*GlobalScope.launch {
                        if (getTime()){
                            Log.i("TAGAA", "OnReach: ")
                        }
                        else{
                            Log.i("TAGAA", "NotReach: ")
                        }
                    }*//*
                    return@OnTouchListener true
                }
            MotionEvent.ACTION_UP -> {
                dialog.dismiss()
                Toast.makeText(v.context, "Up", Toast.LENGTH_SHORT).show()
                return@OnTouchListener true
            } // if you want to handle the touch event

                MotionEvent.ACTION_MOVE ->{
                    if (y>v.pivotY+200 || y<v.pivotY-200)
                        Log.i("TAGAAA", "Move $y form ${v.pivotY}")
                    //Toast.makeText(v.context, "Move $y", Toast.LENGTH_SHORT).show()
                    return@OnTouchListener true
                }
            }


            false
        })*/

    }

}