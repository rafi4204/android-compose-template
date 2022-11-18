package com.monstarlab.features.resources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monstarlab.core.domain.model.Resource
import com.monstarlab.databinding.ItemResourceBinding

class ResourceAdapter : RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder>() {

    private val resources = mutableListOf<Resource>()

    fun updateResources(list: List<Resource>) {
        resources.clear()
        resources.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val itemBinding =
            ItemResourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResourceViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.bind(resources[position])
    }

    override fun getItemCount(): Int {
        return resources.size
    }

    inner class ResourceViewHolder(private val itemBinding: ItemResourceBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(resource: Resource) {
            itemBinding.resourceTitleTextView.text = resource.name
        }
    }
}
