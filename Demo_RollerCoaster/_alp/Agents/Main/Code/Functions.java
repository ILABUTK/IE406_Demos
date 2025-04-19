double fSetCam(boolean fpv)
{/*ALCODESTART::1744992792632*/
if (fpv) {

	window3d.setCamera(vCurrent.camera, true); // true: to follow

	window3d.setNavigationMode( WINDOW_3D_NAVIGATION_NONE );

} else {

	window3d.setCamera(camera, false); 

	// window3d.setNavigationMode( WINDOW_3D_NAVIGATION_LIMITED_TO_Z_ABOVE_ZERO );
	window3d.setNavigationMode( WINDOW_3D_NAVIGATION_FULL );
} 
/*ALCODEEND*/}

