# ytoCommonAlert
公共弹框组件
引用方式如下：
step 1:
Add it in your root build.gradle at the end of repositories:
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
step 2:
Add the dependency in your mainModel build.gradle
dependencies {
	        implementation 'com.github.shegang:ytoCommonAlert:1.0'
	}
  
step 3:
in your xmllayout add:
      <com.yto.common.notice.marqueeview.MarqueeView
                      android:id="@+id/marqueeView"
                      android:layout_width="match_parent"
                      android:layout_height="wrap_content"
                      android:layout_marginLeft="50dp"
                      android:layout_marginRight="50dp"
                      android:layout_marginTop="20dp"
                      app:layout_constraintBottom_toBottomOf="parent"
                      app:layout_constraintLeft_toLeftOf="parent"
                      app:layout_constraintRight_toRightOf="parent"
                      app:layout_constraintTop_toTopOf="parent"
                      android:background="@drawable/bg_large_oval_radius"/>
        
  List<ComplexItemEntity> complexDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            complexDatas.add(new ComplexItemEntity("标题 " + i, "副标题 " + i, "时间 " + i));
        }
        MarqueeFactory<RelativeLayout, ComplexItemEntity> marqueeFactory = new ComplexViewMF(MainActivity.this);

        marqueeFactory.setData(complexDatas);
        marqueeView.setOnItemClickListener(new OnItemClickListener<RelativeLayout, ComplexItemEntity>() {
            @Override
            public void onItemClickListener(RelativeLayout mView, ComplexItemEntity mData, int mPosition) {
                Toast.makeText(MainActivity.this, String.format("mPosition:%s,mData:%s,mView:%s,.", mPosition, mData, mView), Toast.LENGTH_SHORT).show();
            }
        });
        marqueeView.setInAndOutAnim(R.anim.in_top, R.anim.out_bottom);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
        
        以上是轮播公告view的使用方式，下面是介绍Dialog的引用方式如下：
         new SGDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_home_ad)
                .setScreenHeightAspect(this, 0.7f)
                .setScreenWidthAspect(this, 0.8f)
                .setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {

                    }
                })
                .addOnClickListener(R.id.iv_close,R.id.iv_icon)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, SGDialog tDialog) {
                       if(view.getId() == R.id.iv_icon){
                           //可对图片进行修改
                           Toast.makeText(MainActivity.this,"点击了图片",Toast.LENGTH_LONG).show();
                       }
                        tDialog.dismiss();
                    }
                })
                .create()
                .show();
