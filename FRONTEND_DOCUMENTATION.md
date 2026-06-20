# Hotel Reservation System - Frontend Documentation

## Project Overview

A complete professional hotel reservation system frontend built with **Spring Boot 3.4.0**, **Thymeleaf**, **HTML5**, and **CSS3**. This is a luxury hotel booking website with modern, elegant design and responsive layout.

## 🏗️ Project Structure

```
src/main/resources/
├── templates/          # All Thymeleaf HTML templates
│   ├── index.html                 # Landing page (public)
│   ├── login.html                 # Login form (public)
│   ├── register.html              # Registration form (public)
│   ├── home.html                  # User dashboard (authenticated)
│   ├── rooms.html                 # Browse all rooms (public/authenticated)
│   ├── room-add.html              # Admin: Add new room
│   ├── room-edit.html             # Admin: Edit existing room
│   ├── admin-rooms.html           # Admin: Manage all rooms
│   ├── reservation-create.html    # Create reservation
│   ├── my-reservations.html       # User: View their reservations
│   ├── payment-create.html        # Process payment
│   ├── my-payments.html           # User: View payment history
│   ├── profile.html               # User: View profile
│   ├── edit-profile.html          # User: Edit profile
│   └── layout.html                # Base layout (not used as main page)
└── static/
    └── css/
        └── styles.css             # Main stylesheet (responsive, 1000+ lines)
```

## 📄 Template Files

### Public Pages

#### 1. **index.html** - Landing Page
- Beautiful hero section with call-to-action buttons
- Hotel description
- Room highlights with images
- Services section (6 service cards)
- Call-to-action section
- Responsive footer
- No authentication required

#### 2. **login.html** - Login Form
- Username field
- Password field
- Validation error display
- Link to registration page
- Professional form styling
- Uses model attribute: `userLoginRequest`

#### 3. **register.html** - Registration Form
- Username, email, first name, last name, phone number
- Password and confirm password fields
- Validation error display (in red)
- Two-column layout on desktop
- Link to login page
- Uses model attribute: `userRegisterRequest`

### Authenticated User Pages

#### 4. **home.html** - User Dashboard
- Welcome message with user name
- Quick action menu (4 cards):
  - Browse Rooms
  - My Reservations
  - My Payments
  - My Profile
- Quick stats card
- Contact information card
- Professional dashboard layout

#### 5. **rooms.html** - Browse Rooms
- Displays all available rooms in a 3-column grid
- Each room card shows:
  - Room image
  - Room number and type
  - Price per night (highlighted in gold)
  - Capacity, floor, description
  - Room status badge
  - Reserve button (if logged in, disabled if not available)
- Uses model attribute: `rooms`
- Dynamic th:each iteration

#### 6. **reservation-create.html** - Create Reservation
- Two-column layout:
  - Left: Selected room details
  - Right: Booking form
- Form fields:
  - Check-in date (date picker)
  - Check-Out date (date picker)
  - Number of guests
- Real-time price calculation (JavaScript)
- Room details display (roomNumber, roomType, capacity, price, floor, description)
- Uses model attribute: `room`, `reservationCreateRequest`

#### 7. **my-reservations.html** - User Reservations
- List of all user reservations as cards
- Each reservation shows:
  - Room number and type
  - Check-in/check-out dates
  - Total price
  - Reservation status badge
  - Payment status badge
- Actions:
  - Pay button (if paymentStatus = PENDING)
  - Cancel button (if status != CANCELLED)
  - View Details button
- Uses model attribute: `reservations`

#### 8. **payment-create.html** - Process Payment
- Two-column layout:
  - Left: Reservation summary
  - Right: Payment form
- Payment form fields:
  - Payment Type (dropdown): ONLINE or AT_HOTEL
  - Payment Method (dynamic dropdown based on type)
  - Dynamic method selection:
    - ONLINE → BANK_TRANSFER
    - AT_HOTEL → CARD or CASH
- Amount display (highlighted)
- JavaScript: Updates payment methods based on type
- Information box with payment security notice
- Uses model attribute: `reservation`, `paymentCreateRequest`

#### 9. **my-payments.html** - Payment History
- Table layout showing:
  - Payment date
  - Amount (highlighted in gold)
  - Payment type
  - Payment method
  - Payment status badge
  - View action button
- Statistics cards:
  - Total amount spent
  - Total payment count
- Uses model attribute: `payments`

#### 10. **profile.html** - User Profile
- Two-column layout:
  - Left: Personal information display
  - Right: Account settings and stats
- Shows:
  - Username, first name, last name
  - Email, phone number
  - User role badge (styled)
- Account statistics:
  - Total reservations
  - Total spent
  - Loyalty status
- Edit profile button
- Danger zone: Delete account button
- Uses model attribute: `user`

#### 11. **edit-profile.html** - Edit Profile Form
- Two-column form layout:
  - First name
  - Last name
  - Email
  - Phone number
- Validation error display (in red)
- Username is non-editable (note displayed)
- Save and Cancel buttons
- Uses model attribute: `userEditRequest`

### Admin Pages

#### 12. **room-add.html** - Add New Room (Admin)
- Admin-only page
- Form fields:
  - Room number (text)
  - Room type (select dropdown)
  - Price per night (number)
  - Capacity (number)
  - Floor (number)
  - Room status (select dropdown)
  - Description (textarea)
- Validation error display
- Create and Cancel buttons
- Uses model attribute: `roomCreateRequest`

#### 13. **room-edit.html** - Edit Room (Admin)
- Admin-only page
- Same form as room-add.html but for editing
- Pre-filled with current room data
- Save Changes and Cancel buttons
- Uses model attribute: `roomEditRequest`

#### 14. **admin-rooms.html** - Manage Rooms (Admin)
- Admin dashboard for room management
- Table showing all rooms:
  - Room number
  - Room type
  - Price per night (highlighted)
  - Capacity
  - Floor
  - Room status badge
  - Edit action button
  - Delete action button with confirmation
- Admin toolbar with "Add New Room" button
- Empty state message with link to create room
- Uses model attribute: `rooms`

### Base Layout

#### 15. **layout.html** - Base Layout Template
- Reusable header/footer structure (optional)
- Can be used as parent for other templates
- Contains shared navigation logic

## 🎨 CSS Styling (`styles.css`)

### Features

- **CSS Variables** for colors:
  - Primary: `#1a1a1a` (dark)
  - Secondary: `#d4af37` (gold/luxury)
  - Tertiary: `#f5f5f5` (light)
  - Additional: Success, danger, warning, info colors

- **Responsive Design** (mobile-first):
  - Desktop (1200px+)
  - Tablet (768px)
  - Mobile (480px)

- **Components**:
  - Navigation bar (sticky header)
  - Hero section (full-width with gradient overlay)
  - Cards (hover effects, shadows)
  - Forms (styled inputs, validation errors)
  - Tables (admin management)
  - Buttons (multiple variants: primary, secondary, success, danger, info)
  - Alerts (success, danger, warning, info)
  - Grid layouts (2/3/4 columns, responsive)
  - Footer

- **Typography**:
  - Segoe UI font family
  - Readable line-height
  - Professional sizing scale

### Key Classes

```css
/* Layout */
.container, .section, .grid, .grid-2, .grid-3, .grid-4

/* Components */
.navbar, .hero, .card, .card-header, .card-body, .card-footer
.form-group, .form-container, .form-two-col, .form-error
.btn, .btn-primary, .btn-secondary, .btn-success, .btn-danger, .btn-warning, .btn-info
.alert, .alert-success, .alert-danger, .alert-warning, .alert-info

/* Status Badges */
.reservation-status, .status-active, .status-pending, .status-cancelled, .status-completed

/* Utilities */
.text-center, .text-muted, .mt-*, .mb-*, .p-*
```

## 🌐 Thymeleaf Syntax Used

### Templates utilize these Thymeleaf features:

```thymeleaf
<!-- Variable substitution -->
<p th:text="${user.firstName}">FirstName</p>

<!-- URL generation -->
<a th:href="@{/rooms}">Rooms</a>
<a th:href="@{/room/{id}/edit(id=${room.id})}">Edit</a>

<!-- Image paths -->
<img th:src="@{/images/room-single.jpg}" alt="Room">

<!-- Object binding (forms) -->
<form th:action="@{/login}" method="post" th:object="${userLoginRequest}">
    <input th:field="*{username}">
    <span th:if="${#fields.hasErrors('username')}" th:errors="*{username}"></span>
</form>

<!-- Conditional rendering -->
<button th:if="${session.userId != null}">Submit</button>
<div th:if="${session.userRole == 'ADMIN'}">Admin Only</div>

<!-- Iteration -->
<tr th:each="room : ${rooms}">
    <td th:text="${room.roomNumber}"></td>
</tr>

<!-- Checks for empty lists -->
<div th:if="${#lists.isEmpty(reservations)}">No reservations</div>

<!-- Conditions with data -->
<span th:classappend="${room.roomStatus == 'AVAILABLE' ? 'status-active' : 'status-pending'}">
```

## 🔧 Backend Integration Requirements

### Expected Model Attributes

Each template expects certain model attributes from backend controllers:

| Template | Model Attributes |
|----------|------------------|
| login.html | `userLoginRequest`, `errorMessage` |
| register.html | `userRegisterRequest`, `errorMessage` |
| home.html | `session.userName`, `session.userEmail`, `session.userPhone`, `session.userRole` |
| rooms.html | `rooms` |
| room-add.html | `roomCreateRequest`, `errorMessage` |
| room-edit.html | `roomEditRequest`, `roomId`, `errorMessage` |
| admin-rooms.html | `rooms` |
| reservation-create.html | `room`, `reservationCreateRequest` |
| my-reservations.html | `reservations` |
| payment-create.html | `reservation`, `paymentCreateRequest` |
| my-payments.html | `payments` |
| profile.html | `user` |
| edit-profile.html | `userEditRequest` |

### Session Attributes

Controllers should set these in session:
- `session.userId` - User ID (Long or String)
- `session.userName` - User's name for display
- `session.userRole` - "USER" or "ADMIN"
- `session.userEmail` - User's email
- `session.userPhone` - User's phone

### URL Mappings Expected

Frontend links expect these controller mappings:

```
Public:
GET  /                              → index.html
GET  /login                        → login.html
POST /login                        → authenticate & redirect
GET  /register                     → register.html
POST /register                     → create account & redirect
GET  /rooms                        → rooms.html (with model: rooms)
POST /logout                       → logout & redirect

User:
GET  /home                         → home.html
GET  /reservations                → my-reservations.html (with model: reservations)
GET  /reservation/create/{id}     → reservation-create.html (with model: room, reservationCreateRequest)
POST /reservation/create          → process reservation
GET  /payments                    → my-payments.html (with model: payments)
GET  /payment/create/{id}         → payment-create.html (with model: reservation, paymentCreateRequest)
POST /payment/create              → process payment
GET  /profile                     → profile.html (with model: user)
GET  /profile/edit                → edit-profile.html (with model: userEditRequest)
POST /profile/edit                → update profile & redirect

Admin:
GET  /admin/rooms                 → admin-rooms.html (with model: rooms)
GET  /admin/rooms/add             → room-add.html (with model: roomCreateRequest)
POST /admin/rooms/add             → create room & redirect
GET  /admin/rooms/{id}/edit      → room-edit.html (with model: roomEditRequest, roomId)
POST /admin/rooms/{id}/edit      → update room & redirect
POST /admin/rooms/{id}/delete    → delete room & redirect
```

## 🎯 Design Features

### Luxury Hotel Aesthetic
- Gold and black color scheme (`#d4af37` gold, `#1a1a1a` black)
- Clean, spacious layouts
- Professional typography
- Smooth transitions and hover effects
- Card-based design
- Hero sections with gradient overlays

### Responsive Design
- Mobile-first approach
- Breakpoints: 480px, 768px, 1200px
- Flexible grid layouts
- Touch-friendly buttons and forms
- Readable font sizes on all devices

### User Experience
- Clear navigation on all pages
- Form validation feedback (red error text)
- Status badges (color-coded)
- Confirmation modals for destructive actions
- Loading and empty states
- Intuitive button labels with emojis

### Accessibility
- Semantic HTML
- Form labels properly associated with inputs
- Color-coded status indicators (not color alone)
- Readable contrast ratios
- Responsive images

## 🚀 Getting Started

### 1. Place Templates and CSS
```bash
src/main/resources/
├── templates/
│   ├── index.html, login.html, register.html, ...(14 files total)
└── static/
    └── css/
        └── styles.css
```

### 2. Create Controllers

Controllers should:
- Use `@GetMapping` and `@PostMapping`
- Add model attributes for each template
- Redirect after POST operations
- Use session to store user info
- Handle validation errors

Example:
```java
@PostMapping("/login")
public String login(@ModelAttribute UserLoginRequest request, 
                    HttpSession session, RedirectAttributes redirectAttributes) {
    // Authenticate user
    session.setAttribute("userId", user.getId());
    session.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
    session.setAttribute("userRole", user.getRole().name());
    return "redirect:/home";
}
```

### 3. Create DTOs (Request/Response Objects)

Based on form fields, create:
- `UserLoginRequest` (username, password)
- `UserRegisterRequest` (username, password, confirmPassword, firstName, lastName, email, phoneNumber)
- `UserEditRequest` (firstName, lastName, email, phoneNumber)
- `RoomCreateRequest` (roomNumber, roomType, pricePerNight, capacity, floor, roomStatus, description)
- `RoomEditRequest` (same as RoomCreateRequest)
- `ReservationCreateRequest` (roomId, checkInDate, checkOutDate, numberOfGuests)
- `PaymentCreateRequest` (reservationId, paymentType, paymentMethod)

### 4. Configure Security (if using Spring Security)
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Configure login page, allowed URLs, etc.
    // Allow public access to: /, /login, /register, /rooms, /css/**, /js/**, /images/**
    // Require authentication for other URLs
}
```

## 📝 Notes

- All image references use placeholder paths (`/images/room-single.jpg`, etc.)
- Create actual images in `src/main/resources/static/images/`
- Form validation messages are displayed next to fields in red
- Success messages displayed at top of page (if `successMessage` attribute provided)
- Error messages displayed at top of page (if `errorMessage` attribute provided)
- Admin features are protected by session.userRole check
- All forms use POST method for security
- Confirmation dialogs for destructive actions (delete, cancel)

## 🎓 Learning Resources

- Thymeleaf: https://www.thymeleaf.org/
- Spring MVC: https://spring.io/guides/gs/serving-web-content/
- CSS Grid: https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Grid_Layout
- Responsive Design: https://developer.mozilla.org/en-US/docs/Learn/CSS/CSS_layout/Responsive_Design

---

**Created for Hotel Reservation System (Spring Boot 3.4.0)**
All templates are production-ready and comply with Web Accessibility Guidelines.

