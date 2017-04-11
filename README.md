# Android's LayoutInflater.inflate
Understanding Android's LayoutInflater.inflate()

The general gist is this: **If attachToRoot is set to true, then the layout file specified in the first parameter is inflated and attached to the ViewGroup specified in the second parameter.**

Then the method returns this combined View, with the ViewGroup as the root. **When attachToRoot is false, the layout file from the first parameter is inflated and returned as a View.** The root of the returned View would simply be the root specified in the layout file. In either case, the ViewGroup’s LayoutParams are needed to correctly size and position the View created from the layout file.
Passing in true for attachToRoot results in a layout file’s inflated View being added to the ViewGroup right on the spot. Passing in false for attachToRoot means that the View created from the layout file will get added to the ViewGroup in some other way.

#### attachToRoot Set to True
Imagine we specified a button in an XML layout file with its layout width and layout height set to match_parent.
```XML
<Button xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/action_attach_to_root_true"
        android:id="@+id/button_ok">
</Button>
```
We now want to programmatically add this Button to a LinearLayout inside of a Activity. In our LinearLayout member variable **parent**, we can simply add the button with the following:

```java
inflater.inflate(R.layout.view_button, mLinearLayout, true);
```
We specified that we want to inflate the Button from its layout resource file; we then tell the LayoutInflater that we want to attach it to **parent**. Our layout parameters are honored because we know the Button gets added to a LinearLayout. The Button’s layout params type should be LinearLayout.LayoutParams.

#### attachToRoot Set to False
Let’s take a look at when you would want to set attachToRoot to false. In this scenario, the View specified in the first parameter of inflate() is not attached to the ViewGroup in the second parameter at this point in time.
Recall our Button example from earlier, where we want to attach a custom Button from a layout file to **parent**. We can still attach our Button to **parent** by passing in false for attachToRoot—we just manually add it ourselves afterward.

```java
Button btnAttachToRootFalse = (Button) inflater.inflate(R.layout.view_button,parent,false);
parent.addView(btnAttachToRootFalse);
```
The false attachToRoot example requires a bit more work when we manually add the View to a ViewGroup. Adding our Button to our LinearLayout was more convenient with one line of code when attachToRoot was true.

Let’s look at some scenarios that absolutely require attachToRoot to be false.
**RecyclerView’s children should be inflated with attachToRoot passed in as false. The child views are inflated in onCreateViewHolder().**
RecyclerViews, not us, are responsible for determining when to inflate and present its child Views. The attachToRoot parameter should be false anytime we are not responsible for adding a View to a ViewGroup.

There are a few scenarios in which you will not have a root ViewGroup to pass into inflate(). When creating a custom View for an AlertDialog, you do not yet have access to its parent.

```java
AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
LayoutInflater inflater = getActivity().getLayoutInflater();
View rootView = inflater.inflate(R.layout.fragment_hello_dialog,null,false);
builder.setView(rootView);
builder.setTitle("Alert");
builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
dialog.dismiss();
}
});
return builder.create();
```
In this case, it is okay to pass in null for the root ViewGroup. It turns out that the AlertDialog would override any LayoutParams to match_parent anyway. However, the general rule of thumb is to pass in the parent if you’re able to do so.
