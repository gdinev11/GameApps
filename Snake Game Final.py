# Snake Game with Obstacles

from tkinter import *
import random

#Dimensions Of Game
WIDTH = 700
HEIGHT = 600
SPEED = 150
SPACE_SIZE = 25
BODY_SIZE = 2
RECT_SIZE = 100
SNAKE = "red"
COIN = "yellow"
RECT_ONE = "blue"
RECT_TWO = "blue"
RECT_THREE = "blue"
RECT_FOUR = "blue"
BACKGROUND = "green"

#Design snake, coins, obstacles(rectangles)
class Snake:

    def __init__(self):
        self.body_size = BODY_SIZE
        self.coordinates = []
        self.squares = []

        for i in range(0, BODY_SIZE):
            self.coordinates.append([0, 0])

        for x, y in self.coordinates:
            square = canvas.create_rectangle(x, y, x + SPACE_SIZE, y + SPACE_SIZE, fill = SNAKE, tag = "snake")
            self.squares.append(square)

class Coin:

    def __init__(self):

        x = random.randint(0, (WIDTH / SPACE_SIZE)-1) * SPACE_SIZE
        y = random.randint(0, (HEIGHT / SPACE_SIZE) - 1) * SPACE_SIZE

        self.coordinates = [x, y]

        canvas.create_oval(x, y, x + SPACE_SIZE, y + SPACE_SIZE, fill = COIN, tag = "coin")

class Rectangleone:
    
    def __init__(self):
        x = random.randint(0, (WIDTH - RECT_SIZE))
        y = random.randint(0, (HEIGHT- RECT_SIZE))
  
        self.coordinates = [x, y]
  
        canvas.create_rectangle(x, y, x + RECT_SIZE, y + RECT_SIZE, fill = RECT_ONE, tag = "rectangleone")
        
class Rectangletwo:
   
    def __init__(self):
        x = random.randint(0, (WIDTH - RECT_SIZE))
        y = random.randint(0, (HEIGHT- RECT_SIZE))
  
        self.coordinates = [x, y]
  
        canvas.create_rectangle(x, y, x + RECT_SIZE, y + RECT_SIZE, fill = RECT_TWO, tag = "rectangletwo")
        
class Rectanglethree:
    
    def __init__(self):
        x = random.randint(0, (WIDTH - RECT_SIZE))
        y = random.randint(0, (HEIGHT- RECT_SIZE))
  
        self.coordinates = [x, y]
  
        canvas.create_rectangle(x, y, x + RECT_SIZE, y + RECT_SIZE, fill = RECT_THREE, tag = "rectanglethree")
        
class Rectanglefour:
    
    def __init__(self):
        x = random.randint(0, (WIDTH - RECT_SIZE))
        y = random.randint(0, (HEIGHT- RECT_SIZE))
  
        self.coordinates = [x, y]
  
        canvas.create_rectangle(x, y, x + RECT_SIZE, y + RECT_SIZE, fill = RECT_FOUR, tag = "rectanglefour")

#Movement Of Snake and collision check with coins
def next_turn(snake, coin):

    x, y = snake.coordinates[0]

    if direction == "up":
        y -= SPACE_SIZE
    elif direction == "down":
        y += SPACE_SIZE
    elif direction == "left":
        x -= SPACE_SIZE
    elif direction == "right":
        x += SPACE_SIZE

    snake.coordinates.insert(0, (x, y))
    square = canvas.create_rectangle(x, y, x + SPACE_SIZE, y + SPACE_SIZE, fill = SNAKE)
    snake.squares.insert(0, square)

    if x == coin.coordinates[0] and y == coin.coordinates[1]:

        global score
        score += 1
        label.config(text = "Points:{}".format(score))
        canvas.delete("coin")
        coin = Coin()
        canvas.delete("rectangleone")
        rectangleone = Rectangleone()
        canvas.delete("rectangletwo")
        rectangletwo = Rectangletwo()
        canvas.delete("rectanglethree")
        rectanglethree = Rectanglethree()
        canvas.delete("rectanglefour")
        rectanglefour = Rectanglefour()

    else:

        del snake.coordinates[-1]
        canvas.delete(snake.squares[-1])
        del snake.squares[-1]

    if check_collisions(snake):
        game_over()

    else:
        window.after(SPEED, next_turn, snake, coin)

#Control Direction Of Snake
def change_direction(new_direction):

    global direction

    if new_direction == 'left':
        if direction != 'right':
            direction = new_direction
    elif new_direction == 'right':
        if direction != 'left':
            direction = new_direction
    elif new_direction == 'up':
        if direction != 'down':
            direction = new_direction
    elif new_direction == 'down':
        if direction != 'up':
            direction = new_direction

#Check Snake's Collision & Position
def check_collisions(snake):

    x, y = snake.coordinates[0]

    if x < 0 or x >= WIDTH:
        return True
    elif y < 0 or y >= HEIGHT:
        return True

    for body_part in snake.coordinates[1:]:
        if x == body_part[0] and y == body_part[1]:
            return True
#         elif snake.coordinates[1:] == rectangleone:
#             return True
# 
#     for snake_part in rectangleone.coordinates[1:]:
#         if x == snake_part[0] and y == snake_part[1]:
#             return True
#         
#     for body_part in rectangleone.coordinates[0]:
#         if x == body_part[0] and y == body_part[1]:
#             return True 

    return False

#Gameover Function
def game_over():

    canvas.delete(ALL)
    canvas.create_text(canvas.winfo_width()/2, canvas.winfo_height()/2, font = ('Halvetica', 75), text = "YOU LOSE", fill = "black", tag = "gameover")

#Title Window
window = Tk()
window.title("Snake Game")

score = 0
direction = 'down'

#Display Score
label = Label(window, text = "Points:{}".format(score), font = ('Halvetica', 20))
label.pack()

canvas = Canvas(window, bg = BACKGROUND, height = HEIGHT, width = WIDTH)
canvas.pack()

window.update()

window_width = window.winfo_width()
window_height = window.winfo_height()
screen_width = window.winfo_screenwidth()
screen_height = window.winfo_screenheight()

x = int((screen_width/2) - (window_width/2))
y = int((screen_height/2) - (window_height/2))

window.geometry(f"{window_width}x{window_height}+{x}+{y}")

window.bind('<Left>', lambda event: change_direction('left'))
window.bind('<Right>', lambda event: change_direction('right'))
window.bind('<Up>', lambda event: change_direction('up'))
window.bind('<Down>', lambda event: change_direction('down'))

snake = Snake()
coin = Coin()
rectangleone = Rectangleone()
rectangletwo = Rectangletwo()
rectanglethree = Rectanglethree()
rectanglefour = Rectanglefour()

next_turn(snake, coin)

window.mainloop()