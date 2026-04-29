import {
  NavigationMenu,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
} from "@/components/ui/navigation-menu";

import { Button } from "@/components/ui/button";


export default function NavigateBar() {
    const logged = localStorage.getItem("logged");
    return (
        <div className="flex sticky top-0 shadow w-full h-13 items-center px-4 z-50 bg-white/85 backdrop-blur">
	        <div className="mx-1">JWebPanel</div>
	        <NavigationMenu>
	        	<NavigationMenuList>
	        		<NavigationMenuItem>
	        			<NavigationMenuLink>
	        				Main page
	        			</NavigationMenuLink>
	        		</NavigationMenuItem>
	        		{logged && (
                        <NavigationMenuItem>
	        			    <NavigationMenuLink>
	        				    Panel
	        			    </NavigationMenuLink>
	        		    </NavigationMenuItem>
                    )}
	        	</NavigationMenuList>
	        </NavigationMenu>
	        <div className="flex items-center ml-auto">
	        	{logged ? (
                    <>
                        <div className = "mr-5">
	        	    	    <span>Logged as: </span>
	        	    	    <span className="font-semibold">{localStorage.getItem("username")}</span>
	        	        </div>
	        	        <Button variant="destructive">Log out</Button>
                    </>
                ) : (
                    <div className="flex gap-4">
	                    <Button variant="outline">Log in</Button>
                        <Button variant="outline">Sign in</Button>
                    </div>
                )}

	        </div>
        </div>
    );
}