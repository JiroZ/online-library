import HomeComponent from './HomeComponent.jsx'
import MyBooksComponent from "./MyBooksCompoenent";

export const MenuItems = [
    {
        title:'Home',
        url:'/',
        cName:'nav-links',
        component: HomeComponent
    },
    {
        title:'My Books',
        url: '/my-books',
        cName: 'nav-links-my-books',
        component: MyBooksComponent
    },
]