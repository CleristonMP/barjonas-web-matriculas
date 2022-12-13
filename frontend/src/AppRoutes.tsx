import Layout from "components";
import {
  unstable_HistoryRouter as HistoryRouter,
  Routes,
  Route,
} from "react-router-dom";
import About from "pages/About";
import Admin from "pages/Admin";
import Auth from "pages/Admin/Auth";
import Login from "pages/Admin/Auth/Login";
import SchoolClassesList from "pages/Admin/SchoolClasses/SchoolClassesList";
import Table from "pages/Admin/SchoolClasses/Table";
import StudentDetails from "pages/Admin/Students/StudentDetails";
import StudentsList from "pages/Admin/Students/StudentsList";
import UserDetails from "pages/Admin/Users/UserDetails";
import UsersList from "pages/Admin/Users/UsersList";
import Home from "pages/Home";
import { styles } from "util/styles";
import { history } from "util/history";
import PrivateRoutes from "components/PrivateRoutes";
import SchoolClassForm from "pages/Admin/SchoolClasses/SchoolClassForm";
import StudentForm from "pages/Admin/Students/StudentForm";
import UserForm from "pages/Admin/Users/UserForm";
import Recover from "pages/Admin/Auth/Recover";

const AppRoutes = () => {
  return (
    // @ts-expect-error
    <HistoryRouter history={history}>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="about" element={<About />} />
          <Route path="auth" element={<Auth />}>
            <Route path="login" element={<Login />} />
            <Route
              path="recover"
              element={<Recover />}
            />
          </Route>

          <Route element={<PrivateRoutes />}>
            <Route path="admin" element={<Admin />}>
              <Route
                path="schoolClasses/:schoolClassId/form"
                element={<SchoolClassForm />}
              />
              <Route path="schoolClasses/:schoolClassId" element={<Table />} />
              <Route path="schoolClasses" element={<SchoolClassesList />} />
              <Route path="students/:studentId/form" element={<StudentForm />} />
              <Route path="students/:studentId" element={<StudentDetails />} />
              <Route path="students" element={<StudentsList />} />
              <Route path="users/:userId/form" element={<UserForm />} />
              <Route path="users/:userId" element={<UserDetails />} />
              <Route path="users" element={<UsersList />} />
            </Route>
          </Route>

          <Route
            path="*"
            element={<h2 style={styles}>There is nothing here.</h2>}
          />
        </Route>
      </Routes>
    </HistoryRouter>
  );
};

export default AppRoutes;
