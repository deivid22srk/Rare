package com.rare.launcher.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rare.launcher.R
import com.rare.launcher.databinding.ItemGameBinding
import com.rare.launcher.model.GameRecord

class LibraryAdapter(
    private val games: List<GameRecord>
) : RecyclerView.Adapter<LibraryAdapter.GameViewHolder>() {
    
    inner class GameViewHolder(
        private val binding: ItemGameBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(game: GameRecord) {
            val metadata = game.productMetadata
            
            binding.tvGameTitle.text = metadata?.title ?: game.appName
            binding.tvGameDeveloper.text = metadata?.developer ?: "Unknown Developer"
            binding.tvGameNamespace.text = "Namespace: ${game.namespace}"
            
            val imageUrl = metadata?.keyImages?.firstOrNull { 
                it.type == "DieselGameBoxTall" || it.type == "Thumbnail"
            }?.url
            
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_game_placeholder)
                    .error(R.drawable.ic_game_placeholder)
                    .into(binding.ivGameImage)
            } else {
                binding.ivGameImage.setImageResource(R.drawable.ic_game_placeholder)
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(games[position])
    }
    
    override fun getItemCount(): Int = games.size
}
