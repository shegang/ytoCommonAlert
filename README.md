# ytoCommonAlert
公共弹框组件
引用方式如下：
第一步:
Add it in your root build.gradle at the end of repositories:
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
第二步:
Add the dependency in your mainModel build.gradle
dependencies {
	        implementation 'com.github.shegang:ytoCommonAlert:1.0'
	}
  
第三部集成引用:
在你的布局文件中加入：
<com.yto.common.notice.marqueeview.SimpleMarqueeView
        android:id="@+id/marqueeView2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="15dp"
        android:layout_marginRight="15dp"
        android:layout_marginTop="20dp"
        app:layout_constraintTop_toBottomOf="@+id/marqueeView"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
       />  
		      
 在文件类中封装数据引用：  
  noticeManager = new NoticeManager.Builder(this)
                .init(marqueeView2)
                .create();
        
 以上是轮播公告view的使用方式，下面是介绍Dialog的引用方式如下：
new DialogManager(getSupportFragmentManager()).init(this);
