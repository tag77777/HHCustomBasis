package a77777_888.me.t.https.hhcustombasis.fragments.employer

import a77777_888.me.t.https.hhcustombasis.R
import a77777_888.me.t.https.hhcustombasis.databinding.FragmentEmployerBinding
import a77777_888.me.t.https.hhcustombasis.fragments.WebViewFragment
import a77777_888.me.t.https.hhcustombasis.fragments.base.BaseFragment
import a77777_888.me.t.https.hhcustombasis.model.EmptyResult
import a77777_888.me.t.https.hhcustombasis.model.ErrorResult
import a77777_888.me.t.https.hhcustombasis.model.PendingResult
import a77777_888.me.t.https.hhcustombasis.model.Result
import a77777_888.me.t.https.hhcustombasis.model.SuccessResult
import a77777_888.me.t.https.hhcustombasis.model.entities.employer.EmployerResponseEntity
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.View.*
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployerFragment : BaseFragment(R.layout.fragment_employer) {

    private lateinit var binding: FragmentEmployerBinding
    override val viewModel: EmployerViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override val resultHandler: (result: Result<*>) -> Unit = {
        binding.run {

                when (it) {
                    is EmptyResult -> {}
                    is PendingResult -> {
                        waitLayout.visibility = VISIBLE
                        errorLayout.visibility = INVISIBLE
                        employerCardView.visibility = INVISIBLE
                    }
                    is SuccessResult -> {
                        waitLayout.visibility = INVISIBLE
                        errorLayout.visibility = INVISIBLE
                        employerCardView.visibility = VISIBLE
                        initUI(it.value as EmployerResponseEntity)
                    }
                    is ErrorResult -> {
                        waitLayout.visibility = INVISIBLE
                        errorLayout.visibility = VISIBLE
                        employerCardView.visibility = INVISIBLE

                        errorTextView.text = "${it.message}\n${it.error}"
                    }
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentEmployerBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val id = requireArguments().getString(EMPLOYER_ID)!!

        binding.tryAgainButton.setOnClickListener { viewModel.getEmployer(id) }
        viewModel.getEmployer(id)
    }

    @Suppress("DEPRECATION")
    private fun initUI(response: EmployerResponseEntity) {

            with(response){

                if (logo_urls?.original == null) binding.logoImageView.visibility = INVISIBLE
                else {
                    binding.logoImageView.visibility = VISIBLE

                    Glide.with(binding.logoImageView)
                        .load(logo_urls.original)
                        .into(binding.logoImageView)
                }

                binding.nameTextView.text = name

                binding.areaTextView.text = area.name

                type?.let {
                    binding.typeTextView.text = when(it) {
                        "company" -> getString(R.string.company)
                        "agency" -> getString(R.string.agency)
                        "project_director" -> getString(R.string.project_director)
                        "private_recruiter" -> getString(R.string.private_recruiter)
                        else -> it
                    }
                }

                if (site_url == null) binding.siteUrlButton.visibility = GONE
                else {
                    binding.siteUrlButton.text = site_url
                    binding.siteUrlButton.setOnClickListener { onSiteButtonPressed(site_url) }
                }

                if (description == null) binding.descriptionTextView.visibility = GONE
                else binding.descriptionTextView.text =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT)
                    else Html.fromHtml(description)

                if (industries.isEmpty())
                    binding.industriesTextView.visibility = GONE
                else industries.forEach{
                    binding.industriesTextView.append(it.name + "\n")
                }

//                binding.alternateUrlButton.text = alternate_url
                binding.alternateUrlButton.setOnClickListener { onSiteButtonPressed(alternate_url) }

                binding.companyFromBrowserButton
                    .setOnClickListener { onCompanyFromBrowserPressed(alternate_url) }
            }
    }

    private fun onSiteButtonPressed(url: String) {
        findNavController().navigate(
            R.id.webViewFragment,
            bundleOf(WebViewFragment.REFERENCE to url)
        )
    }

    private fun onCompanyFromBrowserPressed(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    companion object {
        const val EMPLOYER_ID = "employer_id"
    }

}