import { Link, useNavigate } from "react-router-dom";
import { NavigationMenu, NavigationMenuList, NavigationMenuLink } from "@radix-ui/react-navigation-menu";
import { Avatar, AvatarImage } from '@radix-ui/react-avatar';
import { IoIosLogOut } from 'react-icons/io';
import { MdInventory } from 'react-icons/md';
import { AiOutlineShoppingCart } from 'react-icons/ai';
import { FaPeopleLine } from 'react-icons/fa6';
import NavigationButton from './NavigationButton';  // Ensure the path is correct for your project

interface NavbarProps {
    username: string;
    cash: number;
}

const Navbar = ({ username, cash }: NavbarProps) => {
    const navigate = useNavigate();

    const logout = () => {
        localStorage.removeItem('auth');
        navigate('/login');
    };

    return (
        <NavigationMenu className="bg-gray-800 rounded ml-2 mr-2 mt-2 fixed w-[calc(100%-1rem)] flex items-center justify-between z-10 h-12">
            <NavigationMenuLink className="flex items-center ml-4">
                <a href="https://media.licdn.com/dms/image/D4E03AQHjwroQRk_WPw/profile-displayphoto-shrink_100_100/0/1705959403635?e=1721865600&v=beta&t=C7SW-VSc2cqGH3mpYKeVS2_XiSxalnMpBjEvy2yjqME" target="_blank" rel="noopener noreferrer" className="flex items-center">
                    <Avatar className="h-9 w-9">
                        <AvatarImage src="https://media.licdn.com/dms/image/D4E03AQHjwroQRk_WPw/profile-displayphoto-shrink_100_100/0/1705959403635?e=1721865600&v=beta&t=C7SW-VSc2cqGH3mpYKeVS2_XiSxalnMpBjEvy2yjqME" className="rounded-full" />
                    </Avatar>
                </a>
                <p className="ml-2 text-white">
                    {username} : {cash} $
                </p>
            </NavigationMenuLink>
            <div className="flex flex-row gap-2">
                <NavigationButton icon={MdInventory} navigationLink="card" navigationName="Cards" />
                <NavigationButton icon={AiOutlineShoppingCart} navigationLink="marketplace" navigationName="Market" />
                <NavigationButton icon={FaPeopleLine} navigationLink="vitrine" navigationName="Vitrine" />
            </div>

            <NavigationMenuLink className="navLinkHover">
                    <Link onClick={logout} className="flex flex-row gap-1 mr-4 items-center text-white">
                        <IoIosLogOut color="white" size={24} /> Logout
                    </Link>
                </NavigationMenuLink>
        </NavigationMenu>
    );
};

export default Navbar;




{/* 
                
                 */}