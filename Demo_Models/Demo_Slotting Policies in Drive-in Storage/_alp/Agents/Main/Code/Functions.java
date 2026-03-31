double getFromCell(SKU agent)
{/*ALCODESTART::1628849255132*/
//check if there are no other SKU in front of selected SKU
if (agent.storage.getCell(agent).isAvailableToRetrieve())
	enter.take(agent);
else
	traceln("Resource cannot reach selected SKU because of other SKUs in front of it!");
/*ALCODEEND*/}

double navigate(ViewArea viewArea)
{/*ALCODESTART::1628864332819*/
selectedViewArea = viewArea;
viewArea.navigateTo();
panelGroup.setPos( viewArea.getX(), viewArea.getY() );
/*ALCODEEND*/}

boolean inStoringProcess()
{/*ALCODESTART::1631718270295*/
return (storeInBlue.nWaitingForResource() +
storeInGreen.nWaitingForResource() + 
storeInRed.nWaitingForResource()) > 0;
/*ALCODEEND*/}

