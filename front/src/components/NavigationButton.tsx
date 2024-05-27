import { NavigationMenuItem } from "@radix-ui/react-navigation-menu";
import { NavLink } from "react-router-dom";
import { IconType } from "react-icons";

interface Props {
    navigationLink: string;
    navigationName: string;
    icon: IconType;
}

const NavigationButton = ({ navigationLink, navigationName, icon: Icon }: Props) => {
    return (
        <NavigationMenuItem>
            <NavLink
                to={`/${navigationLink}`}
                className={({ isActive }) =>
                    `flex flex-row justify-center items-center gap-2 rounded px-2 py-0.5 transition ease-in-out hover:scale-105 ${
                        isActive ? 'bg-white text-black' : 'bg-transparent text-white'
                    } hover:bg-white hover:text-black`
                }
            >
                {({ isActive }) => (
                    <>
                        <Icon className={`${isActive ? 'hover:bg-black' : 'hover:bg-white'}`} /> {navigationName}
                    </>
                )}
            </NavLink>
        </NavigationMenuItem>
    );
};

export default NavigationButton;
