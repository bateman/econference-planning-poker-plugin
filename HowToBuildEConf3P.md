

# How to build eConference Planning Poker Plug-in 1.x #

## Build the plugin ##
Follow the [instructions](http://code.google.com/p/econference4/wiki/howtobuild) to build eConference 4

## Checkout the SVN repository ##

After that, open the _Window | Open perspective_ menu (see Fig. 3)...

<p align='center'><a href='http://econference.googlecode.com/svn/wiki/img/svn2.JPG'>http://econference.googlecode.com/svn/wiki/img/svn2.JPG</a> <br>
<p align='center'><b>Figure 3. Display all the available perspectives</b></p>
<br>
<br>

...and select <i>SVN Repository Exploring</i> (see Fig. 4)<br>
<br>
<p align='center'><a href='http://econference.googlecode.com/svn/wiki/img/svn3.JPG'>http://econference.googlecode.com/svn/wiki/img/svn3.JPG</a> <br>
<p align='center'><b>Figure 4. Add the SVN Repository Exploring perpective to the workbench</b></p>
<br>
<br>

After switching to the <i>SVN Repository Exploring</i> perspective, add a new repository as shown below (Fig. 5).<br>
<br>
<p align='center'><a href='http://econference.googlecode.com/svn/wiki/img/svn4.JPG'>http://econference.googlecode.com/svn/wiki/img/svn4.JPG</a> <br>
<p align='center'><b>Figure 5. Add the SVN Repository Location</b></p>
<br>
<br>

Now, enter <code>http://econference-planning-poker-plugin.googlecode.com/svn/trunk/</code> in the url field of the wizard and click the <i>Finish</i> button. Accept the Digital Certificate when prompted.<br>
<br>
<br>

Once Subclipse has finished fetching data from the new repository, you will see the repository name show up in the tree table viewer on the left pane. Browse <i>Trunk</i>, then select all the plugins and right-click and choose <i>Checkout...</i> from the menu.<br>
<br>
A 2-steps wizard will guide you. You are going to checkout each plugin as a separate project into your workspace. It could take a while, so be patient.<br>
<br>
<h2>Building the workspace</h2>
Once either the checkout or the import is complete, you can build your workspace, but just make sure that the Java compiler compliance level is set to 1.6 (see Fig. 8)<br>
<br>
<br>
<p align='center'><a href='http://econference.googlecode.com/svn/wiki/img/javasettings.JPG'>http://econference.googlecode.com/svn/wiki/img/javasettings.JPG</a> <br>
<p align='center'><b>Figure 8. Set the Java Compiler Compliance Level to 1.6</b></p>
<br>
<br>

<h2>Launching the product</h2>
To launch the tool for the first time, open the <code>it.uniba.di.cdg.econference.planningpoker</code> package and open the <code>it.uniba.di.cdg.planningpoker.boot</code> product file. Then just click on the <i>Launch the product</i> link in the <i>Overview</i> tab.<br>
<br>
<p align='center'><a href='http://econference-planning-poker-plugin.googlecode.com/svn/wiki/img/HowtobuildProduct.PNG'>http://econference-planning-poker-plugin.googlecode.com/svn/wiki/img/HowtobuildProduct.PNG</a> <br>
<p align='center'><b>Figure 9.  Launching the product from Eclipse</b></p>
<br>
<br>

If everthing is fine, you should see the tool splashscreen come up. Otherwise, you'll get a popup with error. Sometimes, on some machine, the tool doesn't start because it misses some of the plugins/projects that are supposed to be already in the classpath. To fix this issue, go to the <i>Run | Run ...</i> menu and select the current product configuration under the <i>Eclipse Application</i> (Fig. 10, A). Then press the <i>Validate Plug-in Set</i> button (B). If an error message pops up, then close it and press the <i>Add Required Plug-ins</i> button (C); otherwise here you are already ok (and the problem should be somewhere else :S).<br>
<br>
From now on, you can just run the tool the way you usually do in Eclipse.<br>
<br>
<br>


<h2>Exporting the product</h2>
Apply the same instructions provided <a href='http://code.google.com/p/econference4/wiki/howtobuild#Exporting_the_product'>here</a> for exporting the eConference 4 product.<br>
Make only sure that you apply those instructions on the PlanningPoker plugin product file, shown in Fig. 9.<br>
<br>
<h2>Code & Comments style</h2>
Last but not least, be also sure to stick on the same coding style that has been used so far.<br>
<br>
Provided that you have succesfully configured and built the whole workspace, download <a href='http://cdg.di.uniba.it/uploads/Research/econf_style.zip'>this archive</a>, and finally import the following XML files, <code>codestyle.xml</code> and <code>codetemplates.xml</code> in Eclipse. To do so, just go in the <i>Windows | Preferences...</i> menu, the browse the tree viewer and select <i>Code Style</i> under the <i>Java</i> branch. Import the two XML files in the proper page (see Fig. 15).<br>
<br>
<br>
<p align='center'><a href='http://econference.googlecode.com/svn/wiki/img/formatter.JPG'>http://econference.googlecode.com/svn/wiki/img/formatter.JPG</a> <br>
<p align='center'><b>Figure 15. Import code template and formatter style for the eConference project</b></p>
<br>
<br>


<h2>Troubleshooting</h2>

Please report problems, comments or suggestions sending an <a href='mailto:eConference3P@gmail.com'>email</a>. Thanks.