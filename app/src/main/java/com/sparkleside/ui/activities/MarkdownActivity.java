package com.sparkleside.ui.activities;

import android.os.Bundle;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivityMarkdownBinding;
import com.sparkleside.ui.base.BaseActivity;
import io.noties.markwon.Markwon;
import io.noties.markwon.html.HtmlPlugin;
import io.noties.markwon.image.ImagesPlugin;
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin;
import io.noties.markwon.ext.tables.TablePlugin;
import io.noties.markwon.ext.tasklist.TaskListPlugin;
import io.noties.markwon.syntax.SyntaxHighlightPlugin;
import io.noties.prism4j.Prism4j;
import io.noties.prism4j.bundler.Prism4jBundler;

public class MarkdownActivity extends BaseActivity {
  private ActivityMarkdownBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    binding = ActivityMarkdownBinding.inflate(getLayoutInflater());
    binding.coordinator.setTransitionName("mark");
    setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
    getWindow()
        .setSharedElementEnterTransition(
            new MaterialContainerTransform().addTarget(R.id.coordinator).setDuration(400));
    getWindow()
        .setSharedElementReturnTransition(
            new MaterialContainerTransform().addTarget(R.id.coordinator).setDuration(350));
    super.onCreate(savedInstanceState);
    setContentView(binding.getRoot());
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    Prism4j prism4j = new Prism4j(new Prism4jBundler());
    final Markwon markwon = Markwon.builder(MarkdownActivity.this)
    .usePlugin(StrikethroughPlugin.create())
    .usePlugin(TablePlugin.create(MarkdownActivity.this))
    .usePlugin(TaskListPlugin.create(context))
    .usePlugin(HtmlPlugin.create())
    .usePlugin(ImagesPlugin.create())
    .usePlugin(SyntaxHighlightPlugin.create(prism4j))
    .build();
    markwon.setMarkdown(binding.markdownView, getIntent().getStringExtra("mark"));
  }
}
